package me.albert.amazingbot.events.message

import me.albert.amazingbot.Bot


class PrivateMessageEvent : MessageReceiveEvent() {

    val temp_source: Int = -1


    @Suppress("KotlinConstantConditions")
    val isTempMessage: Boolean
        get() = temp_source != -1

    val isFriendMessage: Boolean
        get() = sub_type == "friend"

    override fun response(message: String, auto_escape: Boolean): Long {
        return Bot.sendPrivateMsg(user_id, message, auto_escape)
    }
}