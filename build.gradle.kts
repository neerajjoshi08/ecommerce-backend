plugins {
    java
    idea

    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)

    alias(libs.plugins.protobuf)

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)

    alias(libs.plugins.spotless)
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

val osName = System.getProperty("os.name").lowercase()
val osArch = System.getProperty("os.arch").lowercase()

val isMacArm =
    osName.contains("mac") &&
        (osArch == "aarch64" || osArch == "arm64")

val grpcVersion = libs.versions.grpc.get()
val protobufVersion = libs.versions.protobuf.java.get()

dependencies {

    // Spring Boot
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.data.jpa)

    // gRPC
    implementation(platform(libs.grpc.bom))

    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.stub)
    implementation(libs.lognet.grpc.starter)

    // Jakarta
    compileOnly(libs.jakarta.annotation)

    // MySQL
    runtimeOnly(libs.mysql.connector)

    // MapStruct
    implementation(libs.mapstruct)
    kapt(libs.mapstruct.processor)

    // Temporal
    implementation(libs.temporal.spring.boot)

    // Kotlin
    implementation(kotlin("reflect"))

    // Tests
    testImplementation(libs.spring.boot.starter.test)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.temporal.testing)
    testImplementation(kotlin("test"))
}

protobuf {

    protoc {
        if (isMacArm) {
            val protocExecutable =
                file(
                    "${System.getProperty("user.home")}/.local/bin/protoc",
                )

            require(protocExecutable.exists() && protocExecutable.canExecute()) {
                """
                Protoc executable not found or not executable:
                $protocExecutable

                Install protoc in ~/.local/bin/protoc
                """.trimIndent()
            }

            path = protocExecutable.absolutePath
        } else {
            artifact =
                "com.google.protobuf:protoc:$protobufVersion"
        }
    }

    plugins {
        create("grpc") {
            if (isMacArm) {
                val grpcExecutable =
                    file(
                        "${System.getProperty("user.home")}/.local/bin/protoc-gen-grpc-java",
                    )

                require(grpcExecutable.exists() && grpcExecutable.canExecute()) {
                    """
                    gRPC executable not found or not executable:
                    $grpcExecutable

                    Install protoc-gen-grpc-java in ~/.local/bin/
                    """.trimIndent()
                }
                path = grpcExecutable.absolutePath
            } else {
                artifact =
                    "io.grpc:protoc-gen-grpc-java:$grpcVersion"
            }
        }
    }

    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
            }
        }
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}

kapt {
    correctErrorTypes = true

    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
    }
}

spotless {

    kotlin {
        target("src/**/*.kt")

        ktlint(libs.versions.ktlint.get())
            .editorConfigOverride(
                mapOf(
                    "max_line_length" to "100",
                    "ktlint_standard_max-line-length" to "enabled",
                ),
            )

        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlinGradle {
        target("*.gradle.kts", "gradle/*.gradle.kts")

        ktlint(libs.versions.ktlint.get())
            .editorConfigOverride(
                mapOf(
                    "max_line_length" to "100",
                    "ktlint_standard_max-line-length" to "enabled",
                ),
            )

        trimTrailingWhitespace()
        endWithNewline()
    }

    java {
        target("src/**/*.java")

        googleJavaFormat(
            libs.versions.google.java.format
                .get(),
        )

        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }

    format("misc") {
        target(
            "**/*.md",
            ".gitignore",
            "**/*.yml",
            "**/*.yaml",
        )

        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    mainClass.set("com.example.product.MainApplication")
}
