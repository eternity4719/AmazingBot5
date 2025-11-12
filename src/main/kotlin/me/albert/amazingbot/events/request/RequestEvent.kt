package me.albert.amazingbot.events.request

import me.albert.amazingbot.events.ABEvent

open class RequestEvent : ABEvent() {
    var request_type: String = ""

}