package me.albert.amazingbot.events.message

import me.albert.amazingbot.Bot
import me.albert.amazingbot.objects.contact.Anonymous
import me.albert.amazingbot.objects.contact.Group
import me.albert.amazingbot.objects.contact.Member
import me.albert.amazingbot.objects.message.ForwardMessage

class GroupMessageEvent : MessageReceiveEvent() {
    var group_id: String = ""


    val anonymous: Anonymous? = null

    fun isAnonymous(): Boolean {
        return getAnonymous() != null
    }

    fun response(message: ForwardMessage?): Long {
        return Bot.sendForwardMessage(group_id, message)
    }

    fun getAnonymous(): Anonymous? {
        return anonymous
    }

    fun getGroup(noCache: Boolean): Group {
        return Bot.getGroupInfo(group_id, noCache)
    }

    fun getMember(noCache: Boolean): Member {
        return Bot.getMemberInfo(group_id, user_id, noCache)
    }

    fun mute(duration: Int): Boolean {
        return Bot.groupMute(group_id, user_id, duration)
    }

    fun kick(reject_add_request: Boolean): Boolean {
        return Bot.groupKick(group_id, user_id, reject_add_request)
    }

    fun setGroupCard(card: String): Boolean {
        return Bot.setGroupCard(group_id, user_id, card)
    }

    fun setAsEssenceMsg(): Boolean {
        return Bot.setEssenceMsg(this.message_id)
    }

    fun setGroupSpecialTitle(title: String?): Boolean {
        return Bot.setGroupSpecialTitle(group_id, user_id, title)
    }

    override fun response(message: String, auto_escape: Boolean): Long {
        return Bot.sendGroupMsg(group_id, message, auto_escape)
    }
}