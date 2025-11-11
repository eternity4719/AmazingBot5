package me.albert.amazingbot.objects.info

import com.google.gson.annotations.SerializedName

class VersionInfo {
    val appName: String? = null

    val appVersion: String? = null

    val appFullName: String? = null

    val protocolVersion: String? = null

    val coolqEdition: String? = null

    val coolqDirectory: String? = null

    @SerializedName("go-cqhttp")
    val isGoCQhttp: Boolean = false

    val pluginVersion: String? = null

    val pluginBuildNumber: Int = 0

    val pluginBuildConfiguration: String? = null

    val runtimeVersion: String? = null

    val runtimeOS: String? = null

    val version: String? = null

    val protocol: Int = 0

}