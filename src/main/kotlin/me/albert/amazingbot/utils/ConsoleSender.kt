@file:Suppress("removal")

package me.albert.amazingbot.utils


import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import me.albert.amazingbot.Bot
import me.albert.amazingbot.instance
import me.albert.corelib.utils.launchAsync
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.conversations.Conversation
import org.bukkit.conversations.ConversationAbandonedEvent
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.function.Function
import kotlin.time.Duration.Companion.milliseconds


class ConsoleSender(private val contactID: String, private val isGroup: Boolean) : ConsoleCommandSender {
    private val output = ArrayList<String>()
    private val tempOutPut = ArrayList<String>()
    private var task: Job? = null

    private fun get(): Optional<ConsoleCommandSender> {
        return Optional.of(Bukkit.getServer().consoleSender)
    }


    override fun getServer(): Server {
        return Bukkit.getServer()
    }

    override fun getName(): String {
        return "AmazingBot"
    }


    override fun sendMessage(message: String) {
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
                    response.append(s.replace("§\\S".toRegex(), "")).append("\n")
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


    override fun sendMessage(messages: Array<String>) {
        for (msg in messages) {
            sendMessage(msg)
        }
    }

    // 高版本 Paper 命令反馈走 Adventure 的 sendMessage(Component)，需转成纯文本捕获
    override fun sendMessage(message: Component) {
        sendMessage(PlainTextComponentSerializer.plainText().serialize(message))
    }

    @Deprecated("Deprecated in Java")
    override fun sendMessage(p0: UUID?, p1: String) {

    }

    @Deprecated("Deprecated in Java")
    override fun sendMessage(p0: UUID?, vararg p1: String) {

    }

    override fun isPermissionSet(s: String): Boolean {
        return get().map(Function { c: ConsoleCommandSender -> c.isPermissionSet(s) }).orElse(true)
    }

    override fun isPermissionSet(permission: Permission): Boolean {
        return get().map(Function { c: ConsoleCommandSender -> c.isPermissionSet(permission) }).orElse(true)
    }

    override fun hasPermission(s: String): Boolean {
        return get().map(Function { c: ConsoleCommandSender -> c.hasPermission(s) }).orElse(true)
    }

    override fun hasPermission(permission: Permission): Boolean {
        return get().map(Function { c: ConsoleCommandSender -> c.hasPermission(permission) }).orElse(true)
    }

    override fun isOp(): Boolean {
        return true
    }

    override fun setOp(b: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun spigot(): CommandSender.Spigot {
        return object : CommandSender.Spigot() {
            @Deprecated("Deprecated in Java", ReplaceWith("this@ConsoleSender.sendMessage(component.toPlainText())"))
            override fun sendMessage(component: BaseComponent) {
                this@ConsoleSender.sendMessage(component.toPlainText())
            }

            @Deprecated("Deprecated in Java")
            override fun sendMessage(vararg components: BaseComponent) {
                for (baseComponent in components) {
                    sendMessage(baseComponent)
                }
            }
        }
    }

    override fun name(): Component {
        return Component.empty()
    }

    override fun isConversing(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun acceptConversationInput(s: String) {
    }

    override fun beginConversation(conversation: Conversation): Boolean {
        throw UnsupportedOperationException()
    }

    override fun abandonConversation(conversation: Conversation) {
        throw UnsupportedOperationException()
    }

    override fun abandonConversation(
        conversation: Conversation,
        conversationAbandonedEvent: ConversationAbandonedEvent
    ) {
        throw UnsupportedOperationException()
    }

    override fun sendRawMessage(s: String) {
        sendMessage(s)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("sendMessage(p1)"))
    override fun sendRawMessage(p0: UUID?, p1: String) {
        sendMessage(p1)
    }

    override fun addAttachment(plugin: Plugin, s: String, b: Boolean): PermissionAttachment {
        throw UnsupportedOperationException()
    }

    override fun addAttachment(plugin: Plugin): PermissionAttachment {
        throw UnsupportedOperationException()
    }

    override fun addAttachment(plugin: Plugin, s: String, b: Boolean, i: Int): PermissionAttachment? {
        throw UnsupportedOperationException()
    }

    override fun addAttachment(plugin: Plugin, i: Int): PermissionAttachment? {
        throw UnsupportedOperationException()
    }

    override fun removeAttachment(permissionAttachment: PermissionAttachment) {
        throw UnsupportedOperationException()
    }

    override fun recalculatePermissions() {
        throw UnsupportedOperationException()
    }

    override fun getEffectivePermissions(): Set<PermissionAttachmentInfo> {
        throw UnsupportedOperationException()
    }
}