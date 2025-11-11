package me.albert.amazingbot.objects.contact

import me.albert.amazingbot.Bot
import me.albert.amazingbot.objects.info.group.AtAllStatus
import me.albert.amazingbot.objects.info.group.FileInfo
import me.albert.amazingbot.objects.info.group.GroupFileList
import me.albert.amazingbot.objects.info.honer.GroupHonerInfo
import me.albert.amazingbot.objects.message.Message

class Group {
    val groupID: String = ""

    val groupName: String? = null


    /**
     * @return 群备注
     */
    val groupMemo: String? = null

    val createTime: Long = 0

    val groupLevel: Int = 0

    val memberCount: Int = 0

    val maxMemberCount: Int = 0


    val atAllStatus: AtAllStatus
        get() = Bot.getGroupAtAllStatus(groupID)

    val honerInfo: GroupHonerInfo
        get() = Bot.getGroupHonerInfo(groupID)

    val essenceMsgList: List<Any>
        get() = Bot.getEssenceMsgList(groupID)

    fun sendMsg(msg: String, auto_escape: Boolean): Long {
        return Bot.sendGroupMsg(groupID, msg, auto_escape)
    }

    /**
     * 发送群公告
     */
    fun sendNotice(content: String?): Boolean {
        return Bot.sendGroupNotice(groupID, content)
    }

    val rootFileList: GroupFileList
        get() = Bot.getGroupRootFileList(groupID)

    val fileSystemInfo: FileInfo?
        get() = Bot.getGroupFileSystemInfo(groupID)

    fun getGroupFileURL(fileID: String, busid: Int): String {
        return Bot.getGroupFileURL(groupID, fileID, busid)
    }

    fun getFolderFiles(folderID: String): GroupFileList {
        return Bot.getGroupFolderFiles(groupID, folderID)
    }

    fun getMsgHistory(messageSeq: Long): List<Message> {
        return Bot.getGroupMsgHistory(groupID, messageSeq)
    }

    val memberList: List<Member>
        get() = Bot.getGroupMemberList(groupID)

    fun kick(userID: String, rejectAddRequest: Boolean): Boolean {
        return Bot.groupKick(groupID, userID, rejectAddRequest)
    }

    fun mute(duration: Int, user_id: String): Boolean {
        return Bot.groupMute(groupID, user_id, duration)
    }

    fun getMember(userID: String, noCache: Boolean = false): Member? {
        return Bot.getMemberInfo(groupID, userID, noCache)
    }

    fun toggleWholeMute(enable: Boolean): Boolean {
        return Bot.toggleGroupWholeMute(groupID, enable)
    }

    fun setAdmin(user_id: String, enable: Boolean): Boolean {
        return Bot.setGroupAdmin(groupID, user_id, enable)
    }

    fun anonymousMute(anonymous: Anonymous, duration: Int): Boolean {
        return Bot.groupAnonymousMute(groupID, anonymous, duration)
    }

    fun setGroupName(name: String): Boolean {
        return Bot.setGroupName(groupID, name)
    }

    fun leaveGroup(Dismiss: Boolean): Boolean {
        return Bot.setGroupLeave(groupID, Dismiss)
    }

    /**
     * 设置群头像
     *
     * @param file  图片文件名
     * file 参数支持以下几种格式：
     * 绝对路径, 例如 file:///C:\\Users\Richard\Pictures\1.png, 格式使用 file URI
     * 网络 URL, 例如 http://i1.piimg.com/567571/fdd6e7b6d93f1ef0.jpg
     * Base64 编码, 例如 base64://iVBORw0KGgoAAAANSUhEUgAAABQAAAAVCAIAAADJt1n/AAAA...
     * @param cache 表示是否使用已缓存的文件,1使用0不使用,默认1
     * 目前这个API在登录一段时间后因cookie失效而失效, 请考虑后使用
     */
    fun setGroupPortrait(file: String, cache: Int): Boolean {
        return Bot.setGroupPortrait(groupID, file, cache)
    }

    /**
     * 在不提供 folder 参数的情况下默认上传到根目录
     * 只能上传本地文件, 需要上传 http 文件的话请先调用 download_file API下载
     */
    fun uploadFile(file: String, name: String, folder: String): Boolean {
        return Bot.uploadGroupFile(groupID, file, name, folder)
    }
}