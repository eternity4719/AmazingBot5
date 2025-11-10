package me.albert.amazingbot

import me.albert.amazingbot.bot.BotClient
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.net.URI

class AmazingBot : JavaPlugin() {
    companion object {
        lateinit var instance: AmazingBot
        lateinit var client: BotClient
    }

    override fun onEnable() {
        // Plugin startup logic
        instance = this
        saveDefaultConfig()
        val config = instance.config
        val uri = URI(config.getString("main.URI") ?: "")
        val token = config.getString("main.token") ?: ""
        client = BotClient(uri, token)
        logger.info("AmazingBot Enabled")
    }

    fun getDebug(): Boolean {
        return instance.config.getBoolean("debug")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        reloadConfig()
        sender.sendMessage("§7[§bAmazingBot§7] §b配置文件已经重新加载")
        return true
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
