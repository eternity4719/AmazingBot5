package me.albert.amazingbot.bot

import me.albert.amazingbot.AmazingBot
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI


class BotClient(uri: URI,val token: String) : WebSocketClient(uri), BotApi {

    override fun onOpen(p0: ServerHandshake?) {
        AmazingBot.instance.logger.info("§a机器人连接成功!");
    }

    override fun onMessage(msg: String?) {
        if (AmazingBot.instance.getDebug()) {
            AmazingBot.instance.logger.info("§a(DEBUG): 收到信息: ${msg}")
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        AmazingBot.instance.logger
            .warning("机器人连接关闭: " + (if (remote) "remote peer" else "us") + " Code: " + code + " Reason: " + reason)
    }

    override fun onError(ex: Exception?) {
        ex?.printStackTrace()
    }
}