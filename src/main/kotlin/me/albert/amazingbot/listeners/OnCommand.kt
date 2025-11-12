package me.albert.amazingbot.listeners

import me.albert.amazingbot.config
import me.albert.amazingbot.events.message.GroupMessageEvent
import me.albert.amazingbot.events.message.MessageReceiveEvent
import me.albert.amazingbot.logger
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
        val sender =
            ConsoleSender(if (event is GroupMessageEvent) event.group_id else event.user_id, event is GroupMessageEvent)


        scheduler.runNextTick {
            Bukkit.dispatchCommand(sender, cmd)
        }

        val log = config.getString("messages.log_command")
            ?.replace("%user%", event.user_id)
            ?.replace("%cmd%", cmd)
            ?.replace("&", "§")

        log?.let { logger.info(it) }
    }


}
