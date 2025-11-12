package me.albert.amazingbot.events.notice.notify

class GroupHonerChangeEvent : GroupNotifyEvent() {
    var honerType: String? = null
        protected set

    val isTalkative: Boolean
        get() = honerType == "talkative"

    val isPerformer: Boolean
        get() = honerType == "performer"

    val isEmotion: Boolean
        get() = honerType == "emotion"
}