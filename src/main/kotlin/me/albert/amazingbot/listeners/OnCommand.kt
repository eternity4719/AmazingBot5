package me.albert.amazingbot.listeners

import me.albert.amazingbot.AmazingBot
import me.albert.amazingbot.config
import me.albert.amazingbot.events.message.MessageReceiveEvent
import me.albert.amazingbot.scheduler
import me.albert.amazingbot.utils.ConsoleSender
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnCommand : Listener {

    companion object {

        private fun isAdmin(userID: String): Boolean {
            return config.getStringList("owners").contains(userID)
        }


        private fun getLabel(): String {
            return config.getString("command_prefix") + " "
        }
    }

    @EventHandler
    fun onCommand(event: MessageReceiveEvent) {
        if (!isAdmin(event.user_id)) return

        val label = getLabel()
        val msg = event.textMessage
        if (!msg.startsWith(label)) return

        event.response("命令已提交")
        val cmd = msg.substring(label.length)
        val sender = ConsoleSender(event.user_id, event.sub_type.contentEquals("group"))


        scheduler.runNextTick {
            Bukkit.dispatchCommand(sender, cmd)
        }

        val log = AmazingBot.getInstance().config.getString("messages.log_command")
            ?.replace("%user%", event.userID.toString())
            ?.replace("%cmd%", cmd)
            ?.replace("&", "§")

        log?.let { AmazingBot.getInstance().logger.info(it) }
    }


}
