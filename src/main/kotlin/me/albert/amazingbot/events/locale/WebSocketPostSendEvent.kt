package me.albert.amazingbot.events.locale

import com.google.gson.JsonObject


class WebSocketPostSendEvent(val data: JsonObject) : LocalEvent()