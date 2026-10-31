
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.2.0"
        id("org.jetbrains.kotlin.kapt") version "2.2.0"
        id("org.jetbrains.kotlin.plugin.spring") version "2.2.0"
        id("org.jetbrains.kotlin.plugin.jpa") version "2.2.0"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "ecommerce-backend"
