package me.albert.amazingbot.objects.info.group

class InviteRequest {
    var request_id: Long = 0

    var invitor_uin: Long = 0

    var inviter_nick: String? = null

    var group_id: String = ""

    var group_name: String? = null

    var checked: Boolean = false

    var actor: Long = 0

}