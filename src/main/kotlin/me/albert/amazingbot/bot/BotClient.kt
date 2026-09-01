package me.albert.amazingbot.bot

import com.google.gson.JsonObject
import kotlinx.coroutines.*
import me.albert.amazingbot.*
import me.albert.amazingbot.events.locale.WebSocketConnectedEvent
import me.albert.amazingbot.events.locale.WebSocketPostSendEvent
import me.albert.amazingbot.events.locale.WebSocketPreSendEvent
import me.albert.amazingbot.events.locale.WebSocketReceiveEvent
import me.albert.corelib.utils.gson
import me.albert.corelib.utils.launchAsync
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Runnable
import java.net.URI
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.seconds


class BotClient(uri: URI, token: String) : WebSocketClient(uri, mapOf("Authorization" to "Bearer ${token}")), BotApi {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val responseMap = ConcurrentHashMap<UUID, CompletableDeferred<JsonObject>>()

    @Volatile
    var closed = false

    init {
        connect()
    }

    fun sendJson(objectIn: JsonObject): JsonObject? = runBlocking {
        coroutineScope {
            // 调用事件（前）
            val preEvent = WebSocketPreSendEvent(objectIn)
            preEvent.callEvent()
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
            postEvent.callEvent()
            // 等待响应（带超时）
            try {
                withTimeout(1000 * 60) {
                    deferred.await()
                }
            } catch (e: Throwable) {
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
        scope.launch {
            receiveEvent.callEvent()
        }


        val objectData = receiveEvent.data

        // Bot 事件
        if (objectData.has("post_type")) {
            val abEvent = EventParser(objectData).parseEvent()
            scope.launch {
                abEvent.callEvent()
            }
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
        WebSocketConnectedEvent().callEvent()
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

    /**
     * 关停连接并等读线程收尾。必须等:onDisable 返回后插件 jar 句柄即被关闭,
     * 读线程处理关闭握手时再懒加载类(Draft_6455 等)就炸 ZipFile.ensureOpen。
     */
    fun shutdown() {
        closed = true
        scope.cancel()
        runCatching {
            close()
            repeat(60) {
                if (isClosed) return
                Thread.sleep(50)
            }
            // 3 秒没走完关闭握手(对端无响应)就强拆,别卡关服
            closeConnection(666, "shutdown")
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        if (closed) return
        if (code == 666) {
            closed = true
            return
        }

        logger
            .warning("机器人连接关闭: " + (if (remote) "remote peer" else "us") + " Code: " + code + " Reason: " + reason)

        val delay = config.getInt("main.auto_reconnect")

        logger.info("§a将在" + delay + "秒后再次尝试连接")
        instance.launchAsync {
            delay(delay.seconds)
            if (!closed) {
                reconnect()
            }
        }
    }

    override fun onError(ex: Exception?) {
        ex?.printStackTrace()
    }
}