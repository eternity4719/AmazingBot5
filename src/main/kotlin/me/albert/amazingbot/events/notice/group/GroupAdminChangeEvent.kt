package me.albert.amazingbot.events.notice.group

class GroupAdminChangeEvent : GroupNoticeEvent() {
    var sub_type: String = ""


    val isSet: Boolean
        get() = sub_type.contentEquals("set")
}