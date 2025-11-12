package me.albert.amazingbot.events.notice.group

class GroupMemberDecreaseEvent : GroupNoticeEvent() {
    var sub_type: String = ""

    var operator_id: String = ""


    val isLeave: Boolean
        get() = sub_type.contentEquals("leave")

    val isKick: Boolean
        get() = sub_type.contentEquals("kick")

    val isKickMe: Boolean
        get() = sub_type.contentEquals("kick_me")
}