package me.albert.amazingbot.events.request

import me.albert.amazingbot.Bot

class FriendRequestEvent : RequestEvent() {
    var user_id: String = ""

    var comment: String? = null

    var flag: String = ""


    fun approve(approve: Boolean, remark: String): Boolean {
        return Bot.setFriendAddRequest(flag, approve, remark)
    }
}