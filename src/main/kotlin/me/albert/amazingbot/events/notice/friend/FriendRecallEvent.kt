package me.albert.amazingbot.events.notice.friend

import me.albert.amazingbot.Bot
import me.albert.amazingbot.events.notice.NoticeEvent
import me.albert.amazingbot.objects.message.Message

class FriendRecallEvent : NoticeEvent() {
    var user_id: String = ""

    var message_id: Long = 0


    val message: Message?
        get() = Bot.getMsg(message_id)
}