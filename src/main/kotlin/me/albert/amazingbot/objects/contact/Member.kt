package me.albert.amazingbot.objects.contact

import me.albert.amazingbot.Bot

class Member {
    val group_id: String = ""

    val user_id: String = ""

    val nickname: String? = null

    val card: String? = null

    val sex: String? = null

    val age: Int = 0

    val area: String? = null

    val join_time: Long = 0

    val last_sent_time: Long = 0

    val level: String? = null

    val role: String? = null

    val unfriendly: Boolean = false

    val title: String? = null

    val title_expire_time: Long = 0

    val card_changeable: Boolean = false

    val shut_up_timestamp: Long = 0


    fun sendMsg(msg: String, auto_escape: Boolean = false): Long {
        return Bot.sendPrivateMsg(user_id, group_id, msg, auto_escape)
    }

    fun mute(duration: Int): Boolean {
        return Bot.groupMute(group_id, user_id, duration)
    }

    fun kick(rejectAddRequest: Boolean): Boolean {
        return Bot.groupKick(group_id, user_id, rejectAddRequest)
    }

    fun setCard(card: String): Boolean {
        return Bot.setGroupCard(group_id, user_id, card)
    }

    fun setSpecialTitle(title: String): Boolean {
        return Bot.setGroupSpecialTitle(group_id, user_id, title)
    }

    fun setAdmin(enable: Boolean): Boolean {
        return Bot.setGroupAdmin(group_id, user_id, enable)
    }

    val isAdmin: Boolean
        get() = role == "admin"

    val isOwner: Boolean
        get() = role == "owner"

    val name: String?
        get() = if (card!!.trim { it <= ' ' }.isEmpty()) nickname else card

    val displayName: String?
        get() = if (card!!.isEmpty()) nickname else card
}