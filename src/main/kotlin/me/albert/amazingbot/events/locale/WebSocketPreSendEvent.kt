package me.albert.amazingbot.events.locale

import com.google.gson.JsonObject

class WebSocketPreSendEvent(var data: JsonObject) : LocalEvent()