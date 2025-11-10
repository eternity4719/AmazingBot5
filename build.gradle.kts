plugins {
    kotlin("jvm") version "2.3.0-Beta2"
    kotlin("plugin.serialization") version "2.2.0"
    id("com.gradleup.shadow") version "8.3.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "me.albert"
version = "5.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://repo.tcoded.com/releases") {
        name = "tcoded-releases"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.tcoded:FoliaLib:0.5.1")
    implementation("org.java-websocket:Java-WebSocket:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
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
