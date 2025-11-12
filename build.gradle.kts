plugins {
    kotlin("jvm") version "2.2.20"
    id("com.gradleup.shadow") version "8.3.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    `maven-publish`
}

group = "me.albert"
version = "5.0.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://repo.tcoded.com/releases") {
        name = "tcoded-releases"
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"]) // Java 项目，或 components["kotlin"] 对于 Kotlin Multiplatform
            groupId = "me.albert"
            artifactId = "amazingbot"
        }
    }

    repositories {
        google()
        mavenLocal() // 发布到本地仓库
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("com.tcoded:FoliaLib:0.5.1")
    implementation("org.java-websocket:Java-WebSocket:1.6.0")
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.21")
    }
    shadowJar {
        relocate("com.tcoded.folialib", "me.albert.amazingbot.libs.folialib")
        relocate("org.java_websocket", "me.albert.amazingbot.libs.websocket")
        relocate("org.slf4j", "me.albert.amazingbot.libs.slf4j")

    }
}

val targetJavaVersion = 21

kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}


tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
