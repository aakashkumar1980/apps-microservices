plugins {
    id("java-library")
    id("maven-publish")
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "com.example.tutorial"
version = "0.0.1"
description = "Spring Boot API"

val springBootVersion = "3.3.0"
val ioUtilsVersion = "2.16.1"
val commonsLangVersion = "3.14.0"
val jsonSchemaValidationCore = "1.2.14"
val jsonSchemaValidationVersion = "2.2.14"

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
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    implementation("org.apache.commons:commons-lang3:$commonsLangVersion") { exclude(group = "org.slf4j") }
    implementation("org.apache.commons:commons-io:$ioUtilsVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-couchbase:$springBootVersion") { exclude(group = "org.slf4j") }
    implementation("com.github.java-json-tools:json-schema-core:$jsonSchemaValidationCore") { exclude(group = "org.slf4j") }
    implementation("com.github.fge:json-schema-validator:$jsonSchemaValidationVersion") { exclude(group = "org.slf4j") }
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
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
