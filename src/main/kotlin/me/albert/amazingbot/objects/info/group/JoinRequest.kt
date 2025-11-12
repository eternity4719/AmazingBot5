package me.albert.amazingbot.objects.info.group

class JoinRequest {
    var request_id: Long = 0

    var request_uin: Long = 0

    var request_nick: String? = null

    var message: String? = null

    var group_id: String = ""

    var group_name: String? = null

    var checked: Boolean = false

    var actor: Long = 0

}