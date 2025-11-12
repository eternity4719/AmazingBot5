package me.albert.amazingbot.objects.contact

class Sender {

    var user_id: String = ""


    var nickname: String? = null


    var sex: String? = null


    var age: Int = 0


    var card: String? = null


    var area: String? = null


    var level: String? = null


    var role: String? = null


    var title: String? = null


    val name: String?
        get() = if (card!!.trim { it <= ' ' }.isEmpty()) nickname else card

    val displayName: String?
        get() = if (card!!.isEmpty()) nickname else card
}