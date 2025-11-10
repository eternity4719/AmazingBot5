package me.albert.amazingbot.bot

import me.albert.amazingbot.config
import me.albert.amazingbot.debug
import me.albert.amazingbot.logger
import me.albert.amazingbot.scheduler
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI


class BotClient(uri: URI, token: String) : WebSocketClient(uri, mapOf("Authorization" to "Bearer ${token}")), BotApi {

    @Volatile
    var closed = false

    init {
        connect()
    }


    override fun onOpen(p0: ServerHandshake?) {
        logger.info("§a机器人连接成功!");
    }

    override fun onMessage(msg: String?) {
        if (closed) {
            close(666)
        }
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