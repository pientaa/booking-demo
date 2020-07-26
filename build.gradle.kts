import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.mongobee:mongobee:0.13")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
//    testImplementation("cz.jirutka.spring:embedmongo-spring:1.1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.1.2")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.1.2")
    testImplementation("io.kotest:kotest-property-jvm:4.1.2")
    testImplementation("io.kotest:kotest-extensions-spring:4.1.2")
    testImplementation("io.kotlintest:kotlintest-extensions-spring:3.4.2")
//    testImplementation("io.kotest:kotest-extensions-testcontainers:4.1.2")
//    testImplementation("org.testcontainers:mongodb:1.14.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
        jvmTarget = "1.8"
    }
}
