package me.albert.amazingbot.utils


import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import me.albert.amazingbot.Bot
import me.albert.amazingbot.instance
import me.albert.corelib.utils.launchAsync
import me.albert.corelib.utils.removeColors
import me.albert.corelib.utils.toPlainText
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import kotlin.time.Duration.Companion.milliseconds


/**
 * 命令执行反馈收集器。
 *
 * 高版本 Paper/Folia 的原版命令(Brigadier)在 dispatchCommand 时走原生命令分发器，
 * 反馈只发给原生 CommandSource，手写的 CommandSender 捕获不到（输出会落到控制台）。
 * 因此改用 Paper 的 Server.createCommandSender(Consumer<Component>) 创建原生 backed 的
 * sender，把所有反馈(含原版命令)导入回调，再经防抖合并后发回 QQ。
 */
class ConsoleSender(private val contactID: String, private val isGroup: Boolean) {
    private val output = ArrayList<String>()
    private val tempOutPut = ArrayList<String>()
    private var task: Job? = null

    /** 传给 Bukkit.dispatchCommand 使用的原生 backed sender */
    val sender: CommandSender = Bukkit.getServer().createCommandSender { component ->
        append(component.toPlainText())
    }

    private fun append(message: String) {
        task?.cancel()
        synchronized(tempOutPut) {
            tempOutPut.add(message)
        }
        task = instance.launchAsync {
            delay(200.milliseconds)
            synchronized(output) {
                synchronized(tempOutPut) {
                    output.addAll(tempOutPut)
                    tempOutPut.clear()
                }
                val response = StringBuilder()
                for (s in output) {
                    response.append(s.removeColors()).append("\n")
                }
                val msg = response.toString().trim { it <= ' ' }
                if (msg.isNotEmpty()) {
                    if (isGroup) {
                        Bot.sendGroupMsg(contactID, msg, true)
                    } else {
                        Bot.sendPrivateMsg(contactID, msg, true)
                    }
                    output.clear()
                }
            }
        }
    }
}
