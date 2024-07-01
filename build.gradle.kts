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

    // Reflections
    implementation("org.reflections:reflections:0.10.2")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "systems.rishon.Minestorm"
    }

    archiveFileName.set("server.jar")
}