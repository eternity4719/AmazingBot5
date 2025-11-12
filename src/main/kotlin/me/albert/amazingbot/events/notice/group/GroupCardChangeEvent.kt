package me.albert.amazingbot.events.notice.group

class GroupCardChangeEvent : GroupNoticeEvent() {
    var card_new: String = ""

    var card_old: String = ""

}