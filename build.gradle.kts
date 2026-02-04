
val mod_name: String by project
val mod_version: String by project

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.fabric.loom)
}

base {
    archivesName = mod_name
    version = mod_version
}

repositories {
    mavenCentral()
}


loom {
    accessWidenerPath = file("src/main/resources/zeta.accesswidener")
}

configurations {
    implementation {
        extendsFrom(include.get())
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.kotlin)
    modImplementation(libs.fabric.api)

    // NanoVg
    include(libs.lwjgl.nanovg)
    include(libs.lwjgl.nanovg) {
        artifact {
            classifier = "natives-windows"
        }
    }
    include(libs.lwjgl.nanovg) {
        artifact {
            classifier = "natives-linux"
        }
    }
    include(libs.lwjgl.nanovg) {
        artifact {
            classifier = "natives-macos"
        }
    }
}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand(
            "version" to mod_version,
        )
    }
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

