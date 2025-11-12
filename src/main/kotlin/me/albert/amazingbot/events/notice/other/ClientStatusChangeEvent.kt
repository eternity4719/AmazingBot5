package me.albert.amazingbot.events.notice.other

import me.albert.amazingbot.events.notice.NoticeEvent
import me.albert.amazingbot.objects.info.DeviceInfo

class ClientStatusChangeEvent : NoticeEvent() {
    var client: DeviceInfo? = null

    var online: Boolean = false

}