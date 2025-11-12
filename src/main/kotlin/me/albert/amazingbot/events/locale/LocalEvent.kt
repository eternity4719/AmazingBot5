package me.albert.amazingbot.events.locale

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

open class LocalEvent : Event(true) {
    override fun getHandlers(): HandlerList {
        return handlerList
    }


    companion object {
        @JvmStatic
        val handlerList = HandlerList()

    }
}