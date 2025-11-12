package me.albert.amazingbot.objects.info.status

class BotStatistic {
    var packet_received: Long = 0

    var packet_sent: Long = 0

    var packet_lost: Long = 0

    var message_received: Long = 0

    var message_sent: Long = 0

    var disconnect_times: Int = 0

    var lost_times: Int = 0

    var last_message_time: Long = 0

}