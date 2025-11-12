package me.albert.amazingbot.events.message


import me.albert.amazingbot.Bot
import me.albert.amazingbot.events.ABEvent
import me.albert.amazingbot.objects.contact.Sender
import me.albert.amazingbot.objects.info.ocr.ImageOCR
import me.albert.amazingbot.objects.message.Image
import me.albert.amazingbot.objects.message.Message
import me.albert.amazingbot.utils.MsgUtil
import java.awt.image.BufferedImage
import java.util.regex.Pattern

open class MessageReceiveEvent : ABEvent() {
    var message_type: String = ""


    val sub_type: String = ""


    val message_id: Long = 0


    val user_id: String = ""


    val msg: String = ""


    val raw_message: String = ""


    val font: Int = 0


    val sender: Sender? = null


    val textMessage: String
        /**
         * @return 消息中包含的所有纯文本信息
         */
        get() = MsgUtil.deFormatMsg(msg.replace("(\\[)([\\s\\S]*?)(])".toRegex(), " "))

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
                val imageOCR: ImageOCR = Bot.getImageOCR(imageID) ?: continue
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

    fun response(bufferedImage: BufferedImage): Long {
        return response(MsgUtil.bufferedImgToMsg(bufferedImage))
    }


    fun recall() {
        Bot.recallMessage(this.message_id)
    }


    open fun response(message: String, auto_escape: Boolean): Long {
        return 0
    }
}