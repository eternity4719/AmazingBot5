package me.albert.amazingbot.events.notice.notify

import me.albert.amazingbot.Bot
import me.albert.amazingbot.objects.contact.Group

class PokeEvent : NotifyEvent() {
    var sender_id: String = ""

    var user_id: String = ""

    var target_id: String = ""

    var group_id: String = ""


    fun getGroup(noCache: Boolean): Group? {
        return Bot.getGroupInfo(group_id, noCache)
    }
}