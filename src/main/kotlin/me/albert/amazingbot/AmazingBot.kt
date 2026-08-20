package me.albert.amazingbot

import com.google.gson.Gson

import me.albert.amazingbot.bot.BotClient
import me.albert.amazingbot.listeners.OnCommand
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.net.URI


lateinit var instance: AmazingBot


val config get() = instance.config

var client: BotClient? = null

val Bot get() = client!!

val debug get() = config.getBoolean("debug")

val logger get() = instance.logger

val gson = Gson()

fun stopBot() {
    client?.shutdown()
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
        // Plugin startup logic
        instance = this
        saveDefaultConfig()
        startBot()
        Bukkit.getPluginManager().registerEvents(OnCommand(), this)
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
