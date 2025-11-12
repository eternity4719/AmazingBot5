package me.albert.amazingbot.events.notice.group

class GroupFileUploadEvent : GroupNoticeEvent() {
    var file: File? = null


    inner class File {
        var id: String = ""

        var name: String = ""

        var size: Long = 0

        var busid: Int = 0

    }
}