plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "systems.rishon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    // Minestom
    implementation("net.minestom:minestom-snapshots:73b308673b")

    // Lombok
    implementation("org.projectlombok:lombok:1.18.34")

    // Logging
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "systems.rishon.Minestorm"
    }

    archiveFileName.set("server.jar")
}