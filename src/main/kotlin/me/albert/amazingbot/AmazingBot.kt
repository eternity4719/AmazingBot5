package me.albert.amazingbot

import com.tcoded.folialib.FoliaLib
import me.albert.amazingbot.bot.BotClient
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.net.URI


lateinit var instance: AmazingBot

lateinit var foliaLib: FoliaLib

val scheduler get() = foliaLib.scheduler

val config get() = instance.config

var client: BotClient? = null

val debug get() = config.getBoolean("debug")

val logger get() = instance.logger

fun stopBot() {
    client?.closeConnection(666, "close")
}

fun startBot() {
    val config = instance.config
    val uri = URI(config.getString("main.URI") ?: "")
    val token = config.getString("main.token") ?: ""
    stopBot()
    client = BotClient(uri, token)
}


class AmazingBot : JavaPlugin() {

    override fun onEnable() {
        foliaLib = FoliaLib(this)
        // Plugin startup logic
        instance = this
        saveDefaultConfig()
        startBot()
        logger.info("AmazingBot Enabled")
    }


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        reloadConfig()
        startBot()
        sender.sendMessage("§7[§bAmazingBot§7] §b配置文件已经重新加载")
        return true
    }

    override fun onDisable() {
        // Plugin shutdown logic
        stopBot()
    }
}
