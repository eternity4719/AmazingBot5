package me.albert.amazingbot.bot

import com.google.gson.JsonObject
import kotlinx.coroutines.*
import me.albert.amazingbot.*
import me.albert.amazingbot.events.locale.WebSocketConnectedEvent
import me.albert.amazingbot.events.locale.WebSocketPostSendEvent
import me.albert.amazingbot.events.locale.WebSocketPreSendEvent
import me.albert.amazingbot.events.locale.WebSocketReceiveEvent
import me.albert.amazingbot.utils.callEvent
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Runnable
import java.net.URI
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class BotClient(uri: URI, token: String) : WebSocketClient(uri, mapOf("Authorization" to "Bearer ${token}")), BotApi {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val responseMap = ConcurrentHashMap<UUID, CompletableDeferred<JsonObject>>()

    @Volatile
    var closed = false

    init {
        connect()
    }

    fun sendJson(objectIn: JsonObject, timeoutSeconds: Int = 10): JsonObject? = runBlocking {
        coroutineScope {
            // 调用事件（前）
            val preEvent = WebSocketPreSendEvent(objectIn)
            callEvent(preEvent)
            val obj = preEvent.data

            // 添加 echo 标识
            val uuid = UUID.randomUUID()
            obj.addProperty("echo", uuid.toString())

            // 用 CompletableDeferred 代替 CompletableFuture
            val deferred = CompletableDeferred<JsonObject>()
            responseMap[uuid] = deferred

            // 发送消息
            val msg = obj.toString()
            send(msg)

            // 调用事件（后）
            val postEvent = WebSocketPostSendEvent(obj)
            callEvent(postEvent)

            // 等待响应（带超时）
            try {
                withTimeout(timeoutSeconds * 1000L) {
                    deferred.await()
                }
            } catch (e: TimeoutCancellationException) {
                e.printStackTrace()
                null
            } finally {
                responseMap.remove(uuid)
            }
        }
    }

    fun processMessageRec(msg: String) {
        val obj = try {
            gson.fromJson(msg, JsonObject::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }


        // 前置事件
        val receiveEvent = WebSocketReceiveEvent(obj)
        callEvent(receiveEvent)
        val objectData = receiveEvent.data

        // Bot 事件
        if (objectData.has("post_type")) {
//            val abEvent = BotEventParser(objectData).parseEvent()
//            callEvent(abEvent)
        }

        // 响应 echo
        if (objectData.has("echo")) {
            val echo = objectData["echo"].asString
            runCatching {
                val uuid = UUID.fromString(echo)
                val deferred = responseMap[uuid]
                if (deferred != null && !deferred.isCompleted) {
                    deferred.complete(objectData)
                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }


    override fun onOpen(p0: ServerHandshake?) {
        callEvent(WebSocketConnectedEvent())
        logger.info("§a机器人连接成功!");
    }

    override fun onMessage(msg: String) {
        if (closed) {
            close(666)
        }
        processMessageRec(msg)
        if (debug) {
            logger.info("§a(DEBUG): 收到信息: ${msg}")
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        if (code == 666) {
            closed = true
            return
        }

        logger
            .warning("机器人连接关闭: " + (if (remote) "remote peer" else "us") + " Code: " + code + " Reason: " + reason)

        val delay = config.getInt("main.auto_reconnect")

        logger.info("§a将在" + delay + "秒后再次尝试连接")
        scheduler.runLater(
            Runnable {
                if (!closed) {
                    reconnect()
                }
            },
            delay.toLong()
        )
    }

    override fun onError(ex: Exception?) {
        ex?.printStackTrace()
    }
}