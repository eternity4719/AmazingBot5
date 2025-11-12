package me.albert.amazingbot.events.notice.group

import me.albert.amazingbot.Bot
import me.albert.amazingbot.events.notice.NoticeEvent
import me.albert.amazingbot.objects.contact.Group

open class GroupNoticeEvent : NoticeEvent() {
    var group_id: String = ""


    var user_id: String = ""


    fun getGroup(noCache: Boolean = false): Group? {
        return Bot.getGroupInfo(group_id, noCache)
    }
}