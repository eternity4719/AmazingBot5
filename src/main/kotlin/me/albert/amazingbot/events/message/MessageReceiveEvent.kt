package me.albert.amazingbot.events.message


import me.albert.amazingbot.bot.Bot
import me.albert.amazingbot.events.ABEvent
import me.albert.amazingbot.objects.contact.Sender
import me.albert.amazingbot.objects.info.ocr.ImageOCR
import me.albert.amazingbot.objects.message.Image
import me.albert.amazingbot.objects.message.Message
import me.albert.amazingbot.utils.MsgUtil
import java.awt.image.BufferedImage
import java.util.regex.Pattern

open class MessageReceiveEvent : ABEvent() {
    var messageType: String? = null
        protected set

    var subType: String? = null
        protected set

    var messageID: Long? = null
        protected set

    var userID: Long? = null
        protected set

    var msg: String? = null
        protected set

    var rawMessage: String? = null
        protected set

    var font: Int = 0
        protected set

    var sender: Sender? = null
        protected set


    val textMessage: String
        /**
         * @return 消息中包含的所有纯文本信息
         */
        get() = MsgUtil.deFormatMsg(msg!!.replace("(\\[)([\\s\\S]*?)(])".toRegex(), " "))

    val imageIDList: List<String>
        get() {
            val imageIds: MutableList<String> = ArrayList()
            val pattern = Pattern.compile("(?<=\\[CQ:image,file=)([\\s\\S]*?)(?=,)")
            val matcher = pattern.matcher(msg)
            while (matcher.find()) {
                imageIds.add(matcher.group())
            }
            return imageIds
        }

    val imgOCR: String
        get() {
            val result = StringBuilder()
            for (imageID in imageIDList) {
                val imageOCR: ImageOCR = Bot.getApi().getImageOCR(imageID) ?: continue
                val textDetections = imageOCR.texts
                for (textDetection in textDetections) {
                    result.append(textDetection.text).append("\n")
                }
            }
            return result.toString().trim { it <= ' ' }
        }

    open fun response(message: String?, vararg auto_escape: Boolean): Long {
        return 0
    }

    fun response(message: Message): Long {
        return response(message.message)
    }

    fun response(image: Image): Long {
        return response("[CQ:image,file=" + image.url + "]")
    }

    fun response(bufferedImage: BufferedImage?): Long {
        return response(MsgUtil.bufferedImgToMsg(bufferedImage))
    }


    fun recall() {
        Bot.getApi().recallMessage(this.messageID)
    }


    fun response(message: String?, auto_escape: Boolean): Long {
        return 0
    }
}