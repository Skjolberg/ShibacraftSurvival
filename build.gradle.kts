plugins {
    java
    alias(libs.plugins.shadow)
}

group = "${project.property("group")}"
version = "${project.property("version")}"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    maven("https://repo.alessiodp.com/releases/")
    maven("https://jitpack.io/")
}

dependencies {
    compileOnly(libs.spigot)
    implementation(libs.commandFlow)
    compileOnly(libs.libby)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.shibacraft)
    implementation(libs.configurateCore)
    implementation(libs.configurateYaml)
    compileOnly(libs.luckPerms)
}

tasks {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }
    assemble {
        dependsOn(shadowJar)
    }
    build {
        dependsOn(shadowJar)
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    shadowJar {
        val libs = "net.shibacraft.libs"
        //relocate("me.fixeddev.commandflow", "$libs.cmdFlow")
        //relocate("net.kyori.text", "$libs.kyori")
        //relocate("org.bstats", "$libs.bStats")
        //relocate("de.leonhard.storage", "$libs.leonhardStorage")
        //relocate("org.checkerframework", "$libs.jetbrains")
        //relocate("org.jetbrains.annotations", "$libs.jetbrains")
        archiveFileName.set("${project.name}-${project.version}.jar")
        minimize()
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                "name" to rootProject.name,
                "version" to project.version,
                "group" to project.group,
                "author" to project.property("author"),
                "description" to project.property("description"),
                "minecraftVersion" to project.property("minecraftVersion"),
            )
        }
    }

}