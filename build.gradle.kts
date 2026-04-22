import kotlinx.benchmark.gradle.JvmBenchmarkTarget

plugins {
    kotlin("jvm") version "2.2.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.14"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // For testing Benchmark
    implementation("io.mockk:mockk-jvm:1.14.10-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.14")
    implementation("org.openjdk.jmh:jmh-core:1.37")

    testImplementation(kotlin("test"))
    // For testing Issue1523Test
//    testImplementation("io.mockk:mockk-jvm:1.14.10-SNAPSHOT")
//    testImplementation("io.mockk:mockk-jvm:1.14.9")

    // For testing Issue1496Test
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

benchmark {
    configurations {
        named("main") {
            iterationTime = 10
            iterationTimeUnit = "sec"
            iterations = 3
            warmups = 1
        }
    }
    targets {
        register("main") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.37"
        }
    }
}

tasks.register<JavaExec>("issue1523Benchmark") {
    group = "verification"
    description = "Runs the Issue 1523 MockK dependency-order benchmark."
    dependsOn("mainBenchmarkJar")
    classpath = files(
        layout.buildDirectory.file(
            "benchmarks/main/jars/${project.name}-main-jmh-${project.version}-JMH.jar",
        ),
    )
    mainClass.set("org.openjdk.jmh.Main")
    val benchmarkArgs =
        providers.gradleProperty("issue1523BenchmarkArgs").map { it.split(" ") }.orElse(
            listOf(
                "com.example.demo.Issue1523BenchmarkTest",
                "-wi",
                "1",
                "-i",
                "3",
                "-r",
                "10s",
                "-w",
                "10s",
                "-f",
                "1",
            ),
        )
    doFirst {
        args(benchmarkArgs.get())
    }
}
