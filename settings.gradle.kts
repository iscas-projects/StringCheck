pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

sourceControl {
    gitRepository(uri("https://ghproxy.com/https://github.com/iscas-zac/SPF")) {
        producesModule("gov.nasa:SPF")
    }
}

rootProject.name = "StringCheck"
