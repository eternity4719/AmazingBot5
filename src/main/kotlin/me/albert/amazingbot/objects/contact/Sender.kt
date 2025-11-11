package me.albert.amazingbot.objects.contact

class Sender {

    val user_id: String = ""


    val nickname: String? = null


    val sex: String? = null


    val age: Int = 0


    val card: String? = null


    val area: String? = null


    val level: String? = null


    val role: String? = null


    val title: String? = null


    val name: String?
        get() = if (card!!.trim { it <= ' ' }.isEmpty()) nickname else card

    val displayName: String?
        get() = if (card!!.isEmpty()) nickname else card
}