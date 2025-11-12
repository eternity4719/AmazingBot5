package me.albert.amazingbot.events.request

import me.albert.amazingbot.Bot

class FriendRequestEvent : RequestEvent() {
    val user_id: String = ""

    val comment: String? = null

    val flag: String = ""


    fun approve(approve: Boolean, remark: String): Boolean {
        return Bot.setFriendAddRequest(flag, approve, remark)
    }
}