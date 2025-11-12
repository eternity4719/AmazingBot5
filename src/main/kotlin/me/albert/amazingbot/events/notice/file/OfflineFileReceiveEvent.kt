package me.albert.amazingbot.events.notice.file

import me.albert.amazingbot.events.notice.NoticeEvent

data class OfflineFileReceiveEvent(val file: OfflineFile) : NoticeEvent() {
    var user_id: String = ""


}