package me.albert.amazingbot.objects.info

import com.google.gson.annotations.SerializedName

class VersionInfo {
    var app_name: String? = null

    var appVersion: String? = null

    var app_version: String? = null

    var protocol_version: String? = null

    var coolq_edition: String? = null

    var coolq_directory: String? = null

    @SerializedName("go-cqhttp")
    var isGoCQhttp: Boolean = false

    var plugin_version: String? = null

    var plugin_build_number: Int = 0

    var plugin_build_configuration: String? = null

    var runtime_version: String? = null

    var runtime_os: String? = null

    var version: String? = null

    var protocol: Int = 0

}