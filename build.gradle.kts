plugins {
    id("java-library")
    id("maven-publish")
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.5"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

group = "com.example.tutorial"
version = "0.0.1"
description = "Spring Boot API"

val springBootVersion = "3.5.0"
val ioUtilsVersion = "2.19.0"
val commonsLangVersion = "3.17.0"

repositories {
    mavenCentral()

    // Placeholder for company-provided (in-house Artifactory) repository
    /** maven {
        url = uri("<company-artifactory-url>")
        // Authentication should be handled via SSO, do not add username/password here
    } **/
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.apache.commons:commons-lang3:$commonsLangVersion") { exclude(group = "org.slf4j") }
    implementation("org.apache.commons:commons-io:$ioUtilsVersion")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveFileName.set("${rootProject.name}-${version}.jar")
}

tasks.named("build") {
    dependsOn("bootJar")
}

tasks.test {
    useJUnitPlatform()
}
