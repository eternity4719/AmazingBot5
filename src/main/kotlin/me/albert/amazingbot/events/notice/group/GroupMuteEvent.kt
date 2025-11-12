package me.albert.amazingbot.events.notice.group

class GroupMuteEvent : GroupNoticeEvent() {
    var sub_type: String = ""

    var operator_id: String = ""

    var duration: Long = 0


    val isMute: Boolean
        get() = sub_type.contentEquals("ban")
}