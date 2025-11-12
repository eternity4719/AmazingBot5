package me.albert.amazingbot.objects.contact

import me.albert.amazingbot.Bot

class Member {
    var group_id: String = ""

    var user_id: String = ""

    var nickname: String? = null

    var card: String? = null

    var sex: String? = null

    var age: Int = 0

    var area: String? = null

    var join_time: Long = 0

    var last_sent_time: Long = 0

    var level: String? = null

    var role: String? = null

    var unfriendly: Boolean = false

    var title: String? = null

    var title_expire_time: Long = 0

    var card_changeable: Boolean = false

    var shut_up_timestamp: Long = 0


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