package me.albert.amazingbot.utils

import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.imageio.ImageIO

object MsgUtil {
    fun imageToBase64(image: BufferedImage): String {
        val os = ByteArrayOutputStream()
        try {
            ImageIO.write(image, "png", os)
            return Base64.getEncoder().encodeToString(os.toByteArray())
        } catch (ioe: IOException) {
            throw UncheckedIOException(ioe)
        }
    }

    /**
     * @param str 消息
     * @return 转义后消息字符串
     */
    fun formatMsg(str: String): String {
        return str.replace("&", "&amp;")
            .replace("[", "&#91;")
            .replace("]", "&#93;")
    }

    /**
     * @param str 消息
     * @return 转义前消息字符串
     */
    fun deFormatMsg(str: String): String {
        return str.replace("&amp;", "&")
            .replace("&#91;", "[")
            .replace("&#93;", "]")
    }

    /**
     * @param str CQ码内部字符串
     * @return 转义后字符串
     */
    fun formatCQCode(str: String): String {
        return str.replace("&", "&amp;")
            .replace("[", "&#91;")
            .replace("]", "&#93;")
            .replace(",", "&#44;")
    }

    /**
     * @param str CQ码内部字符串
     * @return 转义前字符串
     */
    fun deFormatCQCode(str: String): String {
        return str.replace("&amp;", "&")
            .replace("&#91;", "[")
            .replace("&#93;", "]")
            .replace("&#44;", ",")
    }


    /**
     * @param file 绝对路径，例如 file:///C:\\Users\Richard\Pictures\1.png
     * 网络 URL，例如 http://i1.piimg.com/567571/fdd6e7b6d93f1ef0.jpg
     * Base64 编码，例如 base64://iVBORw0KGgoAAAANSUhEUgAAABQAAAAVCAIA...
     * @return CQ码
     */
    fun getImageMsg(file: String): String {
        return "[CQ:image,file=${formatCQCode(file)}]"
    }

    fun getRecordMsg(file: String): String {
        return "[CQ:record,file=${formatCQCode(file)}]"
    }

    fun getVideoMsg(file: String): String {
        return "[CQ:video,file=${formatCQCode(file)}]"
    }

    fun getAtMsg(qq: String): String {
        return "[CQ:at,qq=$qq]"
    }

    fun getShareMsg(url: String): String {
        return "[CQ:share,url=${formatCQCode(url)}]"
    }

    /**
     * @param type qq 163 xm 分别表示使用 QQ 音乐、网易云音乐、虾米音乐
     * @param id   歌曲 ID
     */
    fun getMusicMsg(type: String, id: String): String {
        return "[CQ:music,type=${formatCQCode(type)},id=$id]"
    }

    /**
     * @param url     点击后跳转目标 URL
     * @param audio   音乐 URL
     * @param title   标题
     * @param content 内容描述
     * @param image   图片 URL
     */
    fun getCustomMusicMsg(url: String, audio: String, title: String, content: String, image: String): String {
        return String.format(
            "[CQ:music,type=custom,url=%s,audio=%s,title=%s,content=%s,image=%s]",
            formatCQCode(url),
            formatCQCode(audio),
            formatCQCode(title),
            formatCQCode(content),
            formatCQCode(image)
        )
    }


    /**
     * @param text 自定义回复的信息
     * @param id   回复时所引用的消息id, 必须为本群消息.
     * @param qq   自定义回复时的自定义QQ, 如果使用自定义信息必须指定.
     * @param time 自定义回复时的时间, 格式为Unix时间
     * @param seq  起始消息序号, 可通过 Bot.getApi.getMessage().getMessageSeq 获得
     */
    fun getReplyMsg(text: String, id: Long, qq: Long, time: Long, seq: Long): String {
        return String.format("[CQ:reply,text=%s,id=%s,qq=%s,time=%s,seq=%s]", formatCQCode(text), id, qq, time, seq)
    }


    fun getPokeMsg(userID: Long): String {
        return "[CQ:poke,qq=$userID]"
    }

    /**
     * @param id 礼物的类型
     * 0	甜 Wink
     * 1	快乐肥宅水
     * 2	幸运手链
     * 3	卡布奇诺
     * 4	猫咪手表
     * 5	绒绒手套
     * 6	彩虹糖果
     * 7	坚强
     * 8	告白话筒
     * 9	牵你的手
     * 10	可爱猫咪
     * 11	神秘面具
     * 12	我超忙的
     * 13	爱心口罩
     */
    fun getGiftMsg(qq: Long, id: Int): String {
        return "[CQ:gift,qq=$qq,id=$id]"
    }

    fun getXMLMsg(xml: String): String {
        return "[CQ:xml,data=${formatCQCode(xml)}]"
    }

    /**
     * @param json  json内容, json的所有字符串记得实体化处理
     * json中的字符串需要进行转义 :
     * ","=> &#44;
     * "&"=> &amp;
     * "["=> &#91;
     * "]"=> &#93;
     * 否则无法正确得到解析,使用上方formatCQCode方法
     * @param resid 建议填0, 走小程序通道, 其他走富文本通道发送
     */
    fun getJsonMsg(json: String, resid: Int): String {
        formatCQCode(json)
        return "[CQ:json,data=$json,resid=$resid]"
    }

    /**
     * 一种xml的图片消息（装逼大图）
     *
     * @param file      和image的file字段对齐, 支持也是一样的
     * @param minwidth  默认不填为400, 最小width
     * @param minheight 默认不填为400, 最小height
     * @param maxwidth  默认不填为500, 最大width
     * @param maxheight 默认不填为1000, 最大height
     * @param source    分享来源的名称, 可以留空
     * @param icon      分享来源的icon图标url, 可以留空
     * xml 接口的消息都存在风控风险, 请自行兼容发送失败后的处理 ( 可以失败后走普通图片模式 )
     */
    fun getCardImg(
        file: String,
        minwidth: Int,
        minheight: Int,
        maxwidth: Int,
        maxheight: Int,
        source: String,
        icon: String
    ): String {
        return String.format(
            "[CQ:cardimage,file=%s,minwidth=%s,minheight=%s," +
                    "maxwidth=%s,maxheight=%s,source=%s,icon=%s]",
            formatCQCode(file),
            minwidth,
            minheight,
            maxwidth,
            maxheight,
            formatCQCode(source),
            formatCQCode(icon)
        )
    }

    /**
     * 文本转语音
     * 范围: 仅群聊
     * 通过TX的TTS接口, 采用的音源与登录账号的性别有关
     *
     * @param msg 内容
     */
    fun getTTSMsg(msg: String): String {
        return "[CQ:tts,text=${formatCQCode(msg)}]"
    }


    fun bufferedImgToMsg(image: BufferedImage): String {
        return getImageMsg("base64://" + imageToBase64(image))
    }

    fun getFileAsBase64(filePath: String?): String? {
        if (filePath == null) {
            return null
        }
        try {
            val b = Files.readAllBytes(Paths.get(filePath))
            return Base64.getEncoder().encodeToString(b)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }
}