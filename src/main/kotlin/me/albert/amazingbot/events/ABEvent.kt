package me.albert.amazingbot.events

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.concurrent.ConcurrentHashMap

open class ABEvent : Event(true) {
    @Transient
    val metas: ConcurrentHashMap<String, Any> = ConcurrentHashMap()

    var time: Long = 0
        protected set

    var selfID: Long = 0
        protected set

    var postType: String? = null
        protected set

    fun addMeta(key: String, `object`: Any) {
        metas[key] = `object`
    }

    fun getMeta(key: String): Any? {
        if (!hasMeta(key)) return null
        return metas[key]
    }

    fun hasMeta(key: String): Boolean {
        return metas.containsKey(key)
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList: HandlerList = HandlerList()
    }
}