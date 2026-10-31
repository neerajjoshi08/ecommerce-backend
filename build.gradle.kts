plugins {
    id("java")
    id("idea")

    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.6"

    id("com.google.protobuf") version "0.9.5"

    id("org.jetbrains.kotlin.jvm") version "2.2.0"
    id("org.jetbrains.kotlin.kapt") version "2.2.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // gRPC
    implementation(platform("io.grpc:grpc-bom:1.81.0"))

    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")
    implementation("io.github.lognet:grpc-spring-boot-starter:5.2.0")

    // Jakarta
    compileOnly("jakarta.annotation:jakarta.annotation-api")

    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")

    // Temporal
    implementation("io.temporal:temporal-spring-boot-starter:1.40.0")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.temporal:temporal-testing:1.40.0")
    testImplementation(kotlin("test"))

    implementation(kotlin("reflect"))
}

// IntelliJ IDEA
idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

// Local Protobuf executables
val protocExecutable = file(
    "${System.getProperty("user.home")}/.local/bin/protoc"
)

val grpcExecutable = file(
    "${System.getProperty("user.home")}/.local/bin/protoc-gen-grpc-java"
)

// Validate executable paths
require(protocExecutable.exists() && protocExecutable.canExecute()) {
    "Protoc executable not found or not executable: $protocExecutable"
}

require(grpcExecutable.exists() && grpcExecutable.canExecute()) {
    "gRPC executable not found or not executable: $grpcExecutable"
}

// Protobuf and gRPC configuration
protobuf {

    // Use local protoc executable instead of Maven artifact
    protoc {
        path = protocExecutable.absolutePath
    }

    // Use local gRPC code generator instead of Maven artifact
    plugins {
        create("grpc") {
            path = grpcExecutable.absolutePath
        }
    }

    // Generate Java and gRPC sources
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
            }
        }
    }
}

// Tests
tasks.test {
    useJUnitPlatform()
}

// Disable regular JAR
tasks.jar {
    enabled = false
}

// Spring Boot executable JAR
tasks.bootJar {
    mainClass.set("com.example.product.MainApplication")
}

// Java 21
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

// Kotlin 21
kotlin {
    jvmToolchain(21)
}

// KAPT
kapt {
    correctErrorTypes = true
}
