package me.albert.amazingbot.objects.contact

import me.albert.amazingbot.Bot


class Friend {

    val userID: String = ""


    val nickName: String? = null


    val remark: String? = null


    fun sendMsg(msg: String, auto_escape: Boolean): Long {
        return Bot.sendPrivateMsg(userID, msg, auto_escape)
    }

    fun delete(): Boolean {
        return Bot.deleteFriend(userID)
    }
}