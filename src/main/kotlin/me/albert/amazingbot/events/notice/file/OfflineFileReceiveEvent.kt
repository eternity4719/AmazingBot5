package me.albert.amazingbot.events.notice.file

import me.albert.amazingbot.events.notice.NoticeEvent

data class OfflineFileReceiveEvent(val file: OfflineFile) : NoticeEvent() {
    val user_id: Long = 0


}