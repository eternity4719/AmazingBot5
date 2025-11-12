package me.albert.amazingbot.events.notice.other

import me.albert.amazingbot.events.notice.NoticeEvent
import me.albert.amazingbot.objects.info.DeviceInfo

class ClientStatusChangeEvent : NoticeEvent() {
    val client: DeviceInfo? = null

    val online: Boolean = false

}