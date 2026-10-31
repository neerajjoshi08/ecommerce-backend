# Ecommerce Backend

A Kotlin-based backend application built with Spring Boot, Java 21, Gradle, MySQL, gRPC, Temporal and Protocol Buffers.

## Technology Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Kotlin | 2.2.0 |
| Spring Boot | 3.4.4 |
| Gradle | Wrapper |
| Protobuf Gradle Plugin | 0.9.5 |
| Protobuf | 3.25.5 |
| gRPC Java | 1.71.0 |
| MySQL | 8.x |
| MapStruct | 1.5.5.Final |

## Prerequisites

Install:

- JDK 21
- Git
- Docker (optional, for running infrastructure services)

You do not need to install Gradle, Protobuf compiler, or the gRPC Java code generator separately.

The Gradle Wrapper downloads the required Gradle distribution. The Protobuf Gradle plugin resolves platform-specific compiler binaries automatically.

## Clone the Repository

```bash
git clone <repository-url>
cd ecommerce-backend
```

## Operating System Support

The build is designed to work on:

- macOS Apple Silicon (ARM64)
- macOS Intel (x86_64)
- Linux (supported architectures)
- Windows (supported architectures)

No hardcoded local executable paths should be added to `build.gradle.kts`.

## Verify Java

```bash
java -version
```

Expected major version:

```text
21
```

If you have multiple Java versions installed, configure `JAVA_HOME` to point to JDK 21.

### macOS

For Apple Silicon or Intel Macs:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
export PATH="$JAVA_HOME/bin:$PATH"
```

Add these lines to `~/.zshrc` if you want them to persist.

### Linux

Configure `JAVA_HOME` to your JDK 21 installation.

### Windows

Configure the `JAVA_HOME` environment variable to your JDK 21 installation and add `%JAVA_HOME%\bin` to `PATH`.

## Build the Application

### macOS / Linux

```bash
./gradlew clean build
```

If the wrapper is not executable:

```bash
chmod +x gradlew
./gradlew clean build
```

### Windows

```powershell
.\gradlew.bat clean build
```

## Protobuf and gRPC Code Generation

Protobuf and gRPC source files are located in:

```text
src/main/proto/
```

Generate Java and gRPC sources:

```bash
./gradlew generateProto
```

Generated source files are placed under:

```text
build/generated/source/proto/
```

These are generated artifacts. Do not manually edit them or commit them to Git unless the project explicitly requires it.

To compile the generated code:

```bash
./gradlew compileKotlin
```

To inspect generated tasks:

```bash
./gradlew tasks --all
```

## MySQL Configuration

The application reads its database configuration from environment variables.

| Variable | Default | Description |
|---|---|---|
| `MYSQL_HOST` | `localhost` | Database hostname |
| `MYSQL_PORT` | `3306` | Database port |
| `MYSQL_DATABASE` | `ecommerce` | Database name |
| `MYSQL_USER` | `root` | Database username |
| `MYSQL_PASSWORD` | `root` | Database password |

### Run Locally on macOS / Linux

```bash
MYSQL_HOST=localhost \
MYSQL_PORT=3306 \
MYSQL_DATABASE=ecommerce \
MYSQL_USER=root \
MYSQL_PASSWORD=root \
./gradlew bootRun
```

Use the actual host port mapped by your MySQL Docker Compose service. For example, if the Compose mapping is `5306:3306`, use `MYSQL_PORT=5306`.

### Run Locally on Windows PowerShell

```powershell
$env:MYSQL_HOST = "localhost"
$env:MYSQL_PORT = "3306"
$env:MYSQL_DATABASE = "ecommerce"
$env:MYSQL_USER = "root"
$env:MYSQL_PASSWORD = "root"

.\gradlew.bat bootRun
```

### IntelliJ IDEA

1. Open **Run → Edit Configurations**.
2. Select the Spring Boot application.
3. Add the required environment variables.
4. Apply the configuration and run.

Example:

```text
MYSQL_HOST=localhost;MYSQL_PORT=3306;MYSQL_DATABASE=ecommerce;MYSQL_USER=root;MYSQL_PASSWORD=root
```

## Running with Docker

If your project includes Docker Compose infrastructure:

```bash
docker compose up -d
```

Check running containers:

```bash
docker compose ps
```

View logs:

```bash
docker compose logs -f
```

When Spring Boot runs directly on your host machine, connect using `localhost` and the published MySQL port.

When Spring Boot runs inside the same Compose network as MySQL, use the MySQL Compose service name as the hostname and port `3306`.

## gRPC

The default gRPC server port is:

```text
6565
```

Override it using:

```bash
GRPC_PORT=6566 ./gradlew bootRun
```

gRPC services can be registered using the LogNet Spring Boot integration:

```kotlin
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class HelloService : HelloServiceGrpc.HelloServiceImplBase() {
    // Implement RPC methods here
}
```

## MapStruct

MapStruct uses KAPT for annotation processing.

Example:

```kotlin
@Mapper(componentModel = "spring")
interface ProductMapper {
    fun toResponse(product: Product): ProductResponse
}
```

Generated implementations are created during the build. Do not manually instantiate generated mapper implementations.

## Kotlin Reflection

Kotlin reflection is included as a runtime dependency:

```kotlin
implementation(kotlin("reflect"))
```

This is required by Kotlin-aware Spring and Spring Data components.

If you encounter:

```text
kotlin/reflect/full/KClasses
```

check that `kotlin-reflect` is available on the runtime classpath:

```bash
./gradlew dependencyInsight \
  --dependency kotlin-reflect \
  --configuration runtimeClasspath
```

## IntelliJ IDEA Setup

Recommended settings:

1. Use JDK 21 as the Project SDK.
2. Set Gradle JVM to JDK 21.
3. Set Build and Run using to Gradle.
4. Set Run tests using to Gradle.
5. Enable annotation processing if required by your IDE workflow.

Use Gradle to build the project so that KAPT-generated sources are handled consistently.

## Useful Gradle Commands

| Command | Purpose |
|---|---|
| `./gradlew clean` | Remove build output |
| `./gradlew build` | Build and test |
| `./gradlew bootRun` | Run Spring Boot |
| `./gradlew test` | Run tests |
| `./gradlew generateProto` | Generate Protobuf and gRPC sources |
| `./gradlew compileKotlin` | Compile Kotlin |
| `./gradlew dependencies` | Inspect dependencies |
| `./gradlew clean build --refresh-dependencies` | Rebuild and refresh dependencies |

## Troubleshooting

### Protobuf executable not found

Do not configure local executable paths. Verify that the Protobuf Gradle plugin is configured with Maven artifacts:

```kotlin
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.5"
    }

    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.71.0"
        }
    }
}
```

### gRPC blockingV2UnaryCall compilation error

The generated gRPC code and runtime libraries must use compatible versions.

This project uses gRPC Java `1.71.0` for both code generation and runtime dependencies.

Do not upgrade only the generator or only the runtime libraries.

### KAPT tasks missing

Verify that the Kotlin KAPT plugin is applied:

```kotlin
id("org.jetbrains.kotlin.kapt")
```

Then run:

```bash
./gradlew clean kaptKotlin
```

### Kotlin reflection error

Verify:

```kotlin
implementation(kotlin("reflect"))
```

Then run:

```bash
./gradlew clean build --refresh-dependencies
```

### MySQL connection refused

Check:

- MySQL container is running.
- Database credentials are correct.
- The database exists.
- The host port is published correctly.
- The hostname matches where Spring Boot is running.

## Development Guidelines

- Do not commit passwords, tokens, or other secrets.
- Do not commit local machine paths.
- Keep Kotlin plugin versions aligned.
- Keep gRPC generator and runtime versions aligned.
- Do not manually edit generated Protobuf or gRPC sources.
- Use Gradle Wrapper rather than relying on a locally installed Gradle version.
- Keep OS-specific setup outside the shared Gradle build.
