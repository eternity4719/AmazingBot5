package me.albert.amazingbot.events.request

import me.albert.amazingbot.Bot

class GroupRequestJoinEvent : RequestEvent() {
    var group_id: String = ""

    var user_id: String = ""

    var comment: String? = null

    var sub_type: String = ""

    var flag: String = ""


    val isInvite: Boolean
        get() = sub_type.contentEquals("invite")

    fun approve(approve: Boolean, reason: String): Boolean {
        return Bot.setGroupAddRequest(flag, sub_type, approve, reason)
    }
}