package me.albert.amazingbot.bot

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.albert.amazingbot.events.ABEvent
import me.albert.amazingbot.events.message.GroupMessageEvent
import me.albert.amazingbot.events.message.MessageReceiveEvent
import me.albert.amazingbot.events.message.PrivateMessageEvent
import me.albert.amazingbot.events.notice.NoticeEvent
import me.albert.amazingbot.events.notice.file.OfflineFileReceiveEvent
import me.albert.amazingbot.events.notice.friend.FriendRecallEvent
import me.albert.amazingbot.events.notice.group.*
import me.albert.amazingbot.events.notice.notify.GroupHonerChangeEvent
import me.albert.amazingbot.events.notice.notify.GroupLuckyKingEvent
import me.albert.amazingbot.events.notice.notify.NotifyEvent
import me.albert.amazingbot.events.notice.notify.PokeEvent
import me.albert.amazingbot.events.notice.other.ClientStatusChangeEvent
import me.albert.amazingbot.events.notice.other.EssenceMessageEvent
import me.albert.amazingbot.events.request.FriendRequestEvent
import me.albert.amazingbot.events.request.GroupRequestJoinEvent
import me.albert.amazingbot.events.request.RequestEvent

class EventParser(private val dataObj: JsonObject) {

    fun parseEvent(): ABEvent {
        val abEvent = parse(ABEvent::class.java)
        val post_type = abEvent.postType
        when (post_type) {
            "message" -> return parseMessageEvent()
            "notice" -> return parseNoticeEvent()
            "request" -> return parseRequestEvent()
        }
        return abEvent
    }

    fun <T : ABEvent?> parse(eventClass: Class<T>): T {
        return gson.fromJson(dataObj, eventClass)
    }

    private fun parseRequestEvent(): RequestEvent {
        val requestEvent = parse(RequestEvent::class.java)
        when (requestEvent.request_type) {
            "group" -> return parse(GroupRequestJoinEvent::class.java)
            "friend" -> return parse(FriendRequestEvent::class.java)
        }
        return requestEvent
    }


    private fun parseNotifyEvent(): NotifyEvent {
        val notifyEvent = parse(NotifyEvent::class.java)
        when (notifyEvent.sub_type) {
            "poke" -> return parse(PokeEvent::class.java)
            "lucky_king" -> return parse(GroupLuckyKingEvent::class.java)
            "honor" -> return parse(GroupHonerChangeEvent::class.java)
        }
        return notifyEvent
    }

    private fun parseNoticeEvent(): NoticeEvent {
        val noticeEvent = parse(NoticeEvent::class.java)
        val notice_type = noticeEvent.noticeType
        when (notice_type) {
            "group_upload" -> return parse(GroupFileUploadEvent::class.java)
            "group_admin" -> return parse(GroupAdminChangeEvent::class.java)
            "group_decrease" -> return parse(GroupMemberDecreaseEvent::class.java)
            "group_increase" -> return parse(GroupMemberIncreaseEvent::class.java)
            "group_ban" -> return parse(GroupMuteEvent::class.java)
            "group_recall" -> return parse(GroupRecallEvent::class.java)
            "friend_recall" -> return parse(FriendRecallEvent::class.java)
            "group_card" -> return parse(GroupCardChangeEvent::class.java)
            "offline_file" -> return parse(OfflineFileReceiveEvent::class.java)
            "client_status" -> return parse(ClientStatusChangeEvent::class.java)
            "essence" -> return parse(EssenceMessageEvent::class.java)
            "notify" -> return parseNotifyEvent()
        }
        return noticeEvent
    }


    private fun parseMessageEvent(): MessageReceiveEvent {
        val messageReceiveEvent = parse(MessageReceiveEvent::class.java)
        val messageType: String = messageReceiveEvent.message_type
        when (messageType) {
            "group" -> return parse(GroupMessageEvent::class.java)
            "private" -> return parse(PrivateMessageEvent::class.java)
        }
        return messageReceiveEvent
    }


    companion object {
        var gson: Gson = Gson()
    }
}