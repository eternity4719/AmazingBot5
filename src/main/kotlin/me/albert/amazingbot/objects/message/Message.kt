package me.albert.amazingbot.objects.message

import me.albert.amazingbot.objects.contact.Anonymous
import me.albert.amazingbot.objects.contact.Sender

class Message {
    var anonymous: Anonymous? = null


    var font: Int = 0


    var group_id: String = ""


    var user_id: String = ""


    var real_id: Long = 0


    var self_id: Long = 0


    var message: String = ""


    var raw_message: String = ""


    var message_id: Long = 0


    var message_seq: Long = 0


    var message_type: String = ""


    var sub_type: String = ""


    var sender: Sender? = null


    var time: Long = 0

}