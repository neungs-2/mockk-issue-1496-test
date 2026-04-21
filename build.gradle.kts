plugins {
    kotlin("jvm") version "2.2.21"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk-jvm:1.14.10-SNAPSHOT")
//    testImplementation("io.mockk:mockk-jvm:1.14.9")
//    testImplementation("io.mockk:mockk-jvm:1.14.8-SNAPSHOT")
//    testImplementation("io.mockk:mockk-jvm:1.14.7")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}
