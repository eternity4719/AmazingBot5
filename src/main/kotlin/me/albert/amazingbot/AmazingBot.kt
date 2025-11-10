package me.albert.amazingbot

import me.albert.amazingbot.bot.BotClient
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.net.URI

class AmazingBot : JavaPlugin() {
    companion object {
        lateinit var instance: AmazingBot
        var client: BotClient? = null
    }

    override fun onEnable() {
        // Plugin startup logic
        instance = this
        saveDefaultConfig()
        startBot()
        logger.info("AmazingBot Enabled")
    }

    fun stopBot(){
        client?.closeConnection(666,"close")
    }

    fun startBot() {
        val config = instance.config
        val uri = URI(config.getString("main.URI") ?: "")
        val token = config.getString("main.token") ?: ""
        stopBot()
        client = BotClient(uri, token)
    }

    fun getDebug(): Boolean {
        return instance.config.getBoolean("debug")
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
