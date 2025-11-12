package me.albert.amazingbot.events.notice.other

import me.albert.amazingbot.Bot
import me.albert.amazingbot.events.notice.NoticeEvent
import me.albert.amazingbot.objects.contact.Group

class EssenceMessageEvent : NoticeEvent() {
    var sub_type: String = ""

    var sender_id: String = ""

    var operator_id: String = ""

    var message_id: Long = 0

    var group_id: String = ""


    val isAdd: Boolean
        get() = sub_type.contentEquals("add")

    fun getGroup(noCache: Boolean = false): Group? {
        return Bot.getGroupInfo(group_id, noCache)
    }
}