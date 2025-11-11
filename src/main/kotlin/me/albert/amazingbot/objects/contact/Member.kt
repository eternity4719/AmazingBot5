package me.albert.amazingbot.objects.contact

import me.albert.amazingbot.Bot

class Member {
    val groupID: String = ""

    val userID: String = ""

    val nickname: String? = null

    val card: String? = null

    val sex: String? = null

    val age: Int = 0

    val area: String? = null

    val joinTime: Long = 0

    val lastSentTime: Long = 0

    val level: String? = null

    val role: String? = null

    val isUnfriendly: Boolean = false

    val title: String? = null

    val titleExpireTime: Long = 0

    val isCardChangeable: Boolean = false

    val shutUpTimestamp: Long = 0


    fun sendMsg(msg: String, auto_escape: Boolean = false): Long {
        return Bot.sendPrivateMsg(userID, groupID, msg, auto_escape)
    }

    fun mute(duration: Int): Boolean {
        return Bot.groupMute(groupID, userID, duration)
    }

    fun kick(rejectAddRequest: Boolean): Boolean {
        return Bot.groupKick(groupID, userID, rejectAddRequest)
    }

    fun setCard(card: String): Boolean {
        return Bot.setGroupCard(groupID, userID, card)
    }

    fun setSpecialTitle(title: String): Boolean {
        return Bot.setGroupSpecialTitle(groupID, userID, title)
    }

    fun setAdmin(enable: Boolean): Boolean {
        return Bot.setGroupAdmin(groupID, userID, enable)
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