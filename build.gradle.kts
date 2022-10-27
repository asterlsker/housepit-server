import com.google.common.collect.Iterables.all
import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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


val excludeSubproject = listOf("client");
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

        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // database
        runtimeOnly("com.h2database:h2")
        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
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
        implementation("io.grpc:grpc-kotlin-stub:${rootProject.properties["grpcKotlinVersion"]}")
        implementation("io.grpc:grpc-protobuf:${rootProject.properties["grpcProtoVersion"]}")
        implementation("com.google.protobuf:protobuf-kotlin:${rootProject.properties["grpcVersion"]}")
    }

    sourceSets {
        getByName("main") {
            java {
                srcDirs(
                    "build/generated/source/proto/main/java",
                    "build/generated/source/proto/main/kotlin"
                )
            }
        }
    }

    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:${rootProject.properties["grpcVersion"]}"
        }
        plugins {
            id("grpc") {
                artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.properties["grpcProtoVersion"]}"
            }
            id("grpckt"){
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
}