package me.albert.amazingbot.objects.info

import com.google.gson.annotations.SerializedName

class VersionInfo {
    val app_name: String? = null

    val appVersion: String? = null

    val app_version: String? = null

    val protocol_version: String? = null

    val coolq_edition: String? = null

    val coolq_directory: String? = null

    @SerializedName("go-cqhttp")
    val isGoCQhttp: Boolean = false

    val plugin_version: String? = null

    val plugin_build_number: Int = 0

    val plugin_build_configuration: String? = null

    val runtime_version: String? = null

    val runtime_os: String? = null

    val version: String? = null

    val protocol: Int = 0

}