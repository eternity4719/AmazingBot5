plugins {
    kotlin("jvm") version "2.3.20"
    id("com.gradleup.shadow") version "9.4.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21"
    `maven-publish`
}

group = "me.albert"
version = "5.0.2"

repositories {
    mavenLocal()
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
            artifactId = "amazingbot"
        }
    }

    repositories {
        google()
        mavenLocal() // 发布到本地仓库
    }
}

dependencies {
    paperweight.foliaDevBundle("26.1.2.build.+")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
    implementation("org.java-websocket:Java-WebSocket:1.6.0")
    compileOnly("me.albert:corelib:1.0.0")
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.21")
    }
    shadowJar {
//        relocate("com.tcoded.folialib", "me.albert.amazingbot.libs.folialib")
        relocate("org.java_websocket", "me.albert.amazingbot.libs.websocket")
//        relocate("org.slf4j", "me.albert.amazingbot.libs.slf4j")

    }
}

val targetJavaVersion = 25

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
