package me.albert.amazingbot.objects.message

import com.google.gson.JsonArray
import com.google.gson.JsonObject

class ForwardMessage(val messages: JsonArray = JsonArray()) {


    fun add(uin: String, name: String, content: String): ForwardMessage {
        val node = JsonObject()
        node.addProperty("type", "node")
        val data = JsonObject()
        data.addProperty("uin", uin)
        data.addProperty("name", name)
        data.addProperty("content", content)
        node.add("data", data)
        messages.add(node)
        return this
    }
}