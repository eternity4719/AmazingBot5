package me.albert.amazingbot.bot

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.sun.tools.javac.resources.CompilerProperties.Fragments.Anonymous
import jdk.internal.icu.util.VersionInfo
import me.albert.amazingbot.AmazingBot
import me.albert.amazingbot.Bot
import me.albert.amazingbot.client
import me.albert.amazingbot.config
import me.albert.amazingbot.utils.ApiAction
import java.awt.Image
import java.util.*


interface BotApi {
    fun sendGroupMsg(groupID: Long?, msg: String?, auto_escape: Boolean = false): Long {
        var message_id: Long = 0
        val data = ApiAction("send_group_msg")
            .param("group_id", groupID)
            .param("message", msg)
            .param("auto_escape", auto_escape).requestAndGetData()
        if (data != null) {
            message_id = data.asJsonObject["message_id"].asLong
        }
        return message_id
    }

    fun sendPrivateMsg(userID: Long?, msg: String?, auto_escape: Boolean = false): Long {
        var message_id: Long = 0
        val data = ApiAction("send_private_msg")
            .param("user_id", userID)
            .param("message", msg)
            .param("auto_escape", auto_escape).requestAndGetData()
        if (data != null) {
            message_id = data.asJsonObject["message_id"].asLong
        }
        return message_id
    }

    fun recallMessage(messageID: Long): Boolean {
        return ApiAction("delete_msg")
            .param("message_id", messageID)
            .requestAndGetStatus()
    }

    fun deleteFriend(friendID: Long): Boolean {
        return ApiAction("delete_friend")
            .param("friend_id", friendID)
            .requestAndGetStatus()
    }

    fun sendPrivateMsg(userID: Long?, groupID: Long?, msg: String?, auto_escape: Boolean = false): Long {
        var message_id: Long = 0
        val data = ApiAction("send_private_msg")
            .param("user_id", userID)
            .param("message", msg)
            .param("group_id", groupID)
            .param("auto_escape", auto_escape).requestAndGetData()
        if (data != null) {
            message_id = data.asJsonObject["message_id"].asLong
        }
        return message_id
    }

    fun getMsg(messageID: Long): Message? {
        val data = ApiAction("get_msg")
            .param("message_id", messageID).requestAndGetData()
        if (data != null) {
            return Gson().fromJson<Message>(data, Message::class.java)
        }
        return null
    }

    fun getForwardMessage(id: String?): ForwardMessage? {
        val data = ApiAction("get_forward_msg")
            .param("message_id", id).requestAndGetData()
        if (data != null) {
            return ForwardMessage(data.asJsonObject["messages"].asJsonArray)
        }
        return null
    }

    fun getImage(file: String?): Image? {
        val data = ApiAction("get_image")
            .param("file", file).requestAndGetData()
        if (data != null) {
            return Gson().fromJson(data, Image::class.java)
        }
        return null
    }

    fun groupKick(groupID: Long, userID: Long, reject_add_request: Boolean): Boolean {
        return ApiAction("set_group_kick")
            .param("group_id", groupID)
            .param("user_id", userID)
            .param("reject_add_request", reject_add_request)
            .requestAndGetStatus()
    }

    fun groupMute(groupID: Long, userID: Long, duration: Int): Boolean {
        return ApiAction("set_group_ban")
            .param("group_id", groupID)
            .param("user_id", userID)
            .param("duration", duration)
            .requestAndGetStatus()
    }

    fun toggleGroupWholeMute(groupID: Long, enable: Boolean): Boolean {
        return ApiAction("set_group_whole_ban")
            .param("group_id", groupID)
            .param("enable", enable)
            .requestAndGetStatus()
    }

    fun setGroupAdmin(groupID: Long, userID: Long, enable: Boolean): Boolean {
        return ApiAction("set_group_admin")
            .param("group_id", groupID)
            .param("user_id", userID)
            .param("enable", enable)
            .requestAndGetStatus()
    }

    fun groupAnonymousMute(groupID: Long, anonymous: Anonymous, duration: Int): Boolean {
        return ApiAction("set_group_anonymous_ban")
            .param("group_id", groupID)
            .param("anonymous_flag", anonymous.getFlag())
            .param("duration", duration)
            .requestAndGetStatus()
    }

    fun sendRawMsg(msg: String?) {
        Bot.send(msg)
    }

    fun sendRawData(data: JsonObject?): JsonObject {
        val timeout: Int = config.getInt("main.timeout")
        return Bot.sendJson(data, timeout)
    }


    fun setGroupCard(groupID: Long?, userID: Long?, card: String?): Boolean {
        return ApiAction("set_group_card")
            .param("user_id", userID)
            .param("group_id", groupID)
            .param("card", card)
            .requestAndGetStatus()
    }

    fun setGroupName(groupID: Long?, name: String?): Boolean {
        return ApiAction("set_group_card")
            .param("group_id", groupID)
            .param("group_name", name)
            .requestAndGetStatus()
    }

    /**
     * 退出群
     *
     * @param groupID   群号
     * @param isDismiss 是否解散(默认false,为群主时true会解散群)
     */
    fun setGroupLeave(groupID: Long?, isDismiss: Boolean): Boolean {
        return ApiAction("set_group_leave")
            .param("group_id", groupID)
            .param("is_dismiss", isDismiss)
            .requestAndGetStatus()
    }

    fun setGroupSpecialTitle(groupID: Long?, userID: Long, title: String?): Boolean {
        return ApiAction("set_group_special_title")
            .param("group_id", groupID)
            .param("user_id", userID)
            .param("special_title", title)
            .requestAndGetStatus()
    }

    fun setFriendAddRequest(flag: String?, approve: Boolean, remark: String?): Boolean {
        return ApiAction("set_friend_add_request")
            .param("flag", flag)
            .param("approve", approve)
            .param("remark", remark)
            .requestAndGetStatus()
    }

    fun setGroupAddRequest(flag: String?, sub_type: String?, approve: Boolean, reason: String?): Boolean {
        return ApiAction("set_group_add_request")
            .param("flag", flag)
            .param("approve", approve)
            .param("sub_type", sub_type)
            .param("reason", reason)
            .requestAndGetStatus()
    }

    fun getLoginInfo(): LoginInfo? {
        val data = ApiAction("get_login_info").requestAndGetData()
        if (data != null) {
            return Gson().fromJson<LoginInfo>(data, LoginInfo::class.java)
        }
        return null
    }

    fun getQiDianInfo(): QiDianInfo? {
        val data = ApiAction("qidian_get_account_info").requestAndGetData()
        if (data != null) {
            return Gson().fromJson<QiDianInfo>(data, QiDianInfo::class.java)
        }
        return null
    }

    fun getGroupInfo(groupID: Long, nocache: Boolean): Group? {
        val data = ApiAction("get_group_info")
            .param("group_id", groupID)
            .param("no_cache", nocache).requestAndGetData()
        if (data != null) {
            return Gson().fromJson<Group>(data, Group::class.java)
        }
        return null
    }

    fun getStrangerInfo(userID: Long, noCache: Boolean): Stranger? {
        val data = ApiAction("get_stranger_info")
            .param("user_id", userID)
            .param("no_cache", noCache).requestAndGetData()
        if (data != null) {
            return Gson().fromJson<Stranger>(data, Stranger::class.java)
        }
        return null
    }

    fun getMemberInfo(groupID: Long, user_id: Long, no_cache: Boolean): Member? {
        val data = ApiAction("get_group_member_info")
            .param("user_id", user_id)
            .param("group_id", groupID)
            .param("no_cache", no_cache).requestAndGetData()
        if (data != null) {
            return Gson().fromJson(data, Member::class.java)
        }
        return null
    }

    fun getGroupMemberList(group_id: Long): List<Member> {
        val members: MutableList<Member> = ArrayList()
        val data = ApiAction("get_group_member_list")
            .param("group_id", group_id)
            .requestAndGetData()
        if (data != null) {
            for (jsonElement in data.asJsonArray) {
                members.add(Gson().fromJson(jsonElement, Member::class.java))
            }
        }
        return members
    }

    fun getFriendList(): List<Friend> {
        val friends: MutableList<Friend> = ArrayList<Friend>()
        val data = ApiAction("get_group_list").requestAndGetData()
        if (data != null) {
            for (friend in data.asJsonArray) {
                friends.add(Gson().fromJson<Friend>(friend, Friend::class.java))
            }
        }
        return friends
    }

    fun getGroupList(): List<Group> {
        val groups: MutableList<Group> = ArrayList<Group>()
        val data = ApiAction("get_group_list").requestAndGetData()
        if (data != null) {
            for (group in data.asJsonArray) {
                groups.add(Gson().fromJson<Group>(group, Group::class.java))
            }
        }
        return groups
    }

    fun getGroupHonerInfo(group_id: Long): GroupHonerInfo? {
        val data = ApiAction("get_group_honor_info")
            .param("group_id", group_id)
            .param("type", "all")
            .requestAndGetData()
        if (data != null) {
            return Gson().fromJson<GroupHonerInfo>(data, GroupHonerInfo::class.java)
        }
        return null
    }

    fun sendForwardMessage(groupID: Long?, forwardMessage: ForwardMessage): Long {
        var message_id: Long = 0
        val data: JsonElement = ApiAction("send_group_forward_msg")
            .param("group_id", groupID)
            .param("messages", forwardMessage.getMessages())
            .requestAndGetData()
        if (data != null) {
            message_id = data.asJsonObject["message_id"].asLong
        }
        return message_id
    }

    fun getVersionInfo(): VersionInfo? {
        val data = ApiAction("get_version_info")
            .requestAndGetData()
        if (data != null) {
            return Gson().fromJson(data, VersionInfo::class.java)
        }
        return null
    }

    fun restartBot(delay: Long) {
        ApiAction("set_restart")
            .param("delay", delay)
            .request()
    }

    /**
     * 设置群头像
     */
    fun setGroupPortrait(group_id: Long, file: String?, cache: Int): Boolean {
        return ApiAction("set_group_portrait")
            .param("group_id", group_id)
            .param("file", file)
            .param("cache", cache)
            .requestAndGetStatus()
    }

    fun getWordSlices(content: String?): List<String> {
        val result: MutableList<String> = ArrayList()
        val data = ApiAction("get_word_slices")
            .param("content", content).requestAndGetData()
        if (data != null) {
            for (s in data.asJsonObject["slices"].asJsonArray) {
                result.add(s.asString)
            }
        }
        return result
    }

    fun getImageOCR(imageID: String?): ImageOCR? {
        val data = ApiAction("ocr_image")
            .param("image", imageID)
            .requestAndGetData()
        if (data != null) {
            return Gson().fromJson<ImageOCR>(data, ImageOCR::class.java)
        }
        return null
    }

    fun uploadGroupFile(group_id: Long, file: String?, name: String?, folder: String?): Boolean {
        return ApiAction("upload_groupfile")
            .param("group_id", group_id)
            .param("file", file)
            .param("name", name)
            .param("folder", folder)
            .requestAndGetStatus()
    }

    fun getGroupFileSystemInfo(group_id: Long): FileInfo? {
        val data = ApiAction("get_group_file_system_info")
            .param("group_id", group_id).requestAndGetData()
        if (data != null) {
            return Gson().fromJson<FileInfo>(data, FileInfo::class.java)
        }
        return null
    }

    fun getGroupSystemMessage(): GroupSystemMessage? {
        val data = ApiAction("get_group_system_msg")
            .requestAndGetData()
        if (data != null) {
            return Gson().fromJson<GroupSystemMessage>(data, GroupSystemMessage::class.java)
        }
        return null
    }

    fun getGroupRootFileList(group_id: Long): GroupFileList? {
        val data = ApiAction("get_group_root_files")
            .param("group_id", group_id)
            .requestAndGetData()
        if (data != null) {
            return Gson().fromJson<GroupFileList>(data, GroupFileList::class.java)
        }
        return null
    }

    fun getGroupFolderFiles(group_id: Long, folder_id: String?): GroupFileList? {
        val data = ApiAction("get_group_files_by_folder")
            .param("group_id", group_id)
            .param("folder_id", folder_id)
            .requestAndGetData()
        if (data != null) {
            return Gson().fromJson<GroupFileList>(data, GroupFileList::class.java)
        }
        return null
    }

    fun getGroupFileURL(group_id: Long, file_id: String?, busid: Int): String {
        val data = ApiAction("get_group_file_url")
            .param("group_id", group_id)
            .param("file_id", file_id)
            .param("busid", busid)
            .requestAndGetData()
        if (data != null) {
            return data.asJsonObject["url"].asString
        }
        return null
    }

    fun getBotStatus(): BotStatus? {
        val data = ApiAction("get_status")
            .requestAndGetData()
        if (data != null) {
            return Gson().fromJson<BotStatus>(data, BotStatus::class.java)
        }
        return null
    }

    fun downloadFile(url: String?, thread_count: Int, headers: String?): String? {
        val data = ApiAction("download_file")
            .param("url", url)
            .param("thread_count", thread_count)
            .param("headers", headers)
            .requestAndGetData()
        if (data != null) {
            return data.asJsonObject["file"].asString
        }
        return null
    }

    fun getOnlineClients(): List<DeviceInfo> {
        val deviceInfoList: MutableList<DeviceInfo> = ArrayList<DeviceInfo>()
        val data = ApiAction("get_online_clients")
            .requestAndGetData()
        if (data != null) {
            for (jsonElement in data.asJsonObject["clients"].asJsonArray) {
                val deviceInfo: DeviceInfo = Gson().fromJson<DeviceInfo>(jsonElement, DeviceInfo::class.java)
                deviceInfoList.add(deviceInfo)
            }
        }
        return deviceInfoList
    }

    fun getGroupMsgHistory(group_id: Long, message_seq: Long): List<Message> {
        val history: MutableList<Message> = ArrayList<Message>()
        val data = ApiAction("get_group_msg_history")
            .param("group_id", group_id)
            .param("message_seq", message_seq)
            .requestAndGetData()
        if (data != null) {
            for (jsonElement in data.asJsonObject["messages"].asJsonArray) {
                history.add(Gson().fromJson<Message>(jsonElement, Message::class.java))
            }
        }
        return history
    }

    fun sendGroupNotice(group_id: Long, content: String?): Boolean {
        return ApiAction("_send_group_notice")
            .param("group_id", group_id)
            .param("content", content)
            .requestAndGetStatus()
    }

    fun setEssenceMsg(message_id: Long): Boolean {
        return ApiAction("set_essence_msg")
            .param("message_id", message_id)
            .requestAndGetStatus()
    }

    fun deleteEssenceMsg(message_id: Long): Boolean {
        return ApiAction("delete_essence_msg")
            .param("message_id", message_id)
            .requestAndGetStatus()
    }

    fun getVIPInfo(user_id: Long): VIPInfo? {
        val data = ApiAction("_get_vip_info")
            .param("user_id", user_id)
            .requestAndGetData()
        if (data != null) {
            return Gson().fromJson<VIPInfo>(data, VIPInfo::class.java)
        }
        return null
    }

    fun getGroupAtAllStatus(group_id: Long): AtAllStatus? {
        val data = ApiAction("get_group_at_all_remain")
            .param("group_id", group_id)
            .requestAndGetData()
        if (data != null) {
            return Gson().fromJson<AtAllStatus>(data, AtAllStatus::class.java)
        }
        return null
    }

    fun getEssenceMsgList(group_id: Long): List<EssenceMessage> {
        val messages: MutableList<EssenceMessage> = ArrayList<EssenceMessage>()
        val data = ApiAction("get_essence_msg_list")
            .param("group_id", group_id)
            .requestAndGetData()
        if (data != null) {
            for (jsonElement in data.asJsonArray) {
                messages.add(Gson().fromJson<EssenceMessage>(jsonElement, EssenceMessage::class.java))
            }
        }
        return messages
    }

    fun checkURLSafe(url: String?): Int {
        val data = ApiAction("check_url_safely")
            .param("url", url)
            .requestAndGetData()
        if (data != null) {
            return data.asJsonObject["level"].asInt
        }
        return 2
    }

    fun setModelShow(model: String?, model_show: String?): Boolean {
        return ApiAction("_set_model_show")
            .param("model", model)
            .param("model_show", model_show)
            .requestAndGetStatus()
    }


    fun canSendImage(): Boolean {
        val data = ApiAction("can_send_image")
            .requestAndGetData()
        if (data != null) {
            return data.asJsonObject["yes"].asBoolean
        }
        return false
    }

    fun canSendVoice(): Boolean {
        val data = ApiAction("can_send_record")
            .requestAndGetData()
        if (data != null) {
            return data.asJsonObject["yes"].asBoolean
        }
        return false
    }

}