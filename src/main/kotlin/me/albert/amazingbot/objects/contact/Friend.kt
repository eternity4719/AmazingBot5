package me.albert.amazingbot.objects.contact

import me.albert.amazingbot.Bot


class Friend {

    val user_id: String = ""


    val nickname: String? = null


    val remark: String? = null


    fun sendMsg(msg: String, auto_escape: Boolean): Long {
        return Bot.sendPrivateMsg(user_id, msg, auto_escape)
    }

    fun delete(): Boolean {
        return Bot.deleteFriend(user_id)
    }
}