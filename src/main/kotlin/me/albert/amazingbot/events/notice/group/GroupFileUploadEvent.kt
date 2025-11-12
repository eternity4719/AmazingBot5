package me.albert.amazingbot.events.notice.group

class GroupFileUploadEvent : GroupNoticeEvent() {
    val file: File? = null


    inner class File {
        val id: String = ""

        val name: String = ""

        val size: Long = 0

        val busid: Int = 0

    }
}