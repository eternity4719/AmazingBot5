package me.albert.amazingbot.events.locale

import com.google.gson.JsonObject

class WebSocketReceiveEvent(var data: JsonObject) : LocalEvent()