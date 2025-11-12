package me.albert.amazingbot.events.notice.notify

import me.albert.amazingbot.Bot
import me.albert.amazingbot.objects.contact.Group

class PokeEvent : NotifyEvent() {
    val sender_id: String = ""

    val user_id: String = ""

    val target_id: String = ""

    val group_id: String = ""


    fun getGroup(noCache: Boolean): Group? {
        return Bot.getGroupInfo(group_id, noCache)
    }
}