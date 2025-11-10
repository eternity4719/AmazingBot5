package me.albert.amazingbot.utils

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.albert.amazingbot.Bot

class ApiAction(action: String?) {
    val data: JsonObject = JsonObject()

    private val params = JsonObject()

    private var result: JsonObject? = null

    init {
        data.addProperty("action", action)
        data.add("params", params)
    }

    fun param(key: String, value: JsonElement?): ApiAction {
        params.add(key, value)
        data.add("params", params)
        return this
    }

    fun param(key: String, value: String?): ApiAction {
        params.addProperty(key, value)
        data.add("params", params)
        return this
    }

    fun param(key: String, value: Number?): ApiAction {
        params.addProperty(key, value)
        data.add("params", params)
        return this
    }

    fun param(key: String, value: Boolean): ApiAction {
        params.addProperty(key, value)
        data.add("params", params)
        return this
    }

    fun requestAndGetStatus(): Boolean {
        result = Bot.sendRawData(data)
        return result!!["status"].asString == "ok"
    }

    fun requestAndGetData(): JsonElement? {
        result = Bot.sendRawData(data)
        if (!status) return null
        return result!!["data"]
    }

    val status: Boolean
        get() {
            if (result == null) {
                return false
            }
            return result!!["status"].asString == "ok"
        }


    fun request(): JsonObject? {
        result = Bot.sendRawData(data)
        return result
    }
}