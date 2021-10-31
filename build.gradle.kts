plugins {
    kotlin("jvm") version "1.5.31"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(16))
}

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("net.dv8tion:JDA:4.3.0_277")
    implementation("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}