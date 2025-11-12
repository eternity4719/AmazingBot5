package me.albert.amazingbot.events.notice.notify


import me.albert.amazingbot.Bot
import me.albert.amazingbot.objects.contact.Group

open class GroupNotifyEvent : NotifyEvent() {
    val group_id: String = ""

    val user_id: String = ""


    fun getGroup(noCache: Boolean): Group? {
        return Bot.getGroupInfo(group_id, noCache)
    }
}