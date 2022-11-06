import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("com.google.protobuf") version "0.8.18" apply false
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

java.sourceCompatibility = JavaVersion.VERSION_17
tasks.jar { enabled = false }
tasks.bootJar { enabled = false }

allprojects {
    group = "com.asterlker"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}


val excludeSubproject = listOf("client")
configure(subprojects.filter { it.name !in excludeSubproject }) {

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    allOpen {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }

    dependencies {
        // starter
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-webflux")

        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // database
        runtimeOnly("com.h2database:h2")

        // Annotation Processing Tool
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
//            exclude(group = "org.mockito")
        }
        testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
        testImplementation("io.kotest:kotest-runner-junit5:${rootProject.properties["kotestVersion"]}")
        testImplementation("io.kotest:kotest-assertions-core:${rootProject.properties["kotestVersion"]}")
        testImplementation("io.kotest:kotest-property:${rootProject.properties["kotestVersion"]}")
        testImplementation("io.mockk:mockk:1.13.2")
        testImplementation("io.grpc:grpc-testing:1.50.2")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project("housepit-api-server") {
    dependencies {
        // module
        implementation(project(":housepit-core"))
        implementation(project(":client:auth"))

    }
}

project("housepit-core") {

    dependencies {

    }
}

project("client:auth") {
    apply(plugin = "com.google.protobuf")

    dependencies {
        // submodule
        implementation(project(":housepit-core"))

        // grpc
        implementation("io.grpc:grpc-stub:${rootProject.properties["grpcVersion"]}")
        implementation("io.grpc:grpc-kotlin-stub:${rootProject.properties["grpcKotlinVersion"]}")
        implementation("io.grpc:grpc-protobuf:${rootProject.properties["grpcVersion"]}")
        implementation("com.google.protobuf:protobuf-kotlin:${rootProject.properties["protobufVersion"]}")
        runtimeOnly("io.grpc:grpc-netty:${rootProject.ext["grpcVersion"]}")

    }

    sourceSets {
        getByName("main") {
            java {
                srcDirs(
                    "build/generated/source/proto/main/grpc",
                    "build/generated/source/proto/main/grpckt",
                    "build/generated/source/proto/main/java",
                    "build/generated/source/proto/main/kotlin",
                )
            }
        }
    }


    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:${rootProject.properties["protobufVersion"]}"
        }
        plugins {
            id("grpc") {
                artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.properties["grpcVersion"]}"
            }
            id("grpckt") {
                artifact = "io.grpc:protoc-gen-grpc-kotlin:${rootProject.properties["grpcKotlinVersion"]}:jdk8@jar"
            }
        }
        generateProtoTasks {
            all().forEach {
                it.plugins {
                    id("grpc")
                    id("grpckt")
                }
                it.builtins {
                    id("kotlin")
                }
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
        }
    }
}