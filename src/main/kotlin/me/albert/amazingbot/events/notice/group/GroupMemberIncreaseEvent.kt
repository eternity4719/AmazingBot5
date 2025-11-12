package me.albert.amazingbot.events.notice.group

import me.albert.amazingbot.Bot
import me.albert.amazingbot.objects.contact.Member

class GroupMemberIncreaseEvent : GroupNoticeEvent() {
    var sub_type: String = ""

    var operator_id: String = ""


    val isInvite: Boolean
        get() = sub_type.contentEquals("invite")

    fun getMember(noCache: Boolean = false): Member? {
        return Bot.getMemberInfo(group_id, user_id, noCache)
    }

    fun kick(rejectAddRequest: Boolean): Boolean {
        return Bot.groupKick(group_id, user_id, rejectAddRequest)
    }

    fun mute(duration: Int): Boolean {
        return Bot.groupMute(group_id, user_id, duration)
    }
}