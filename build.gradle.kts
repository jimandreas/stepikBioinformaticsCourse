
plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.jimandreas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation( "org.jetbrains.kotlinx:multik-api:0.1.1")
    implementation( "org.jetbrains.kotlinx:multik-default:0.1.1")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    implementation("dev.romainguy:kotlin-math:1.1.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    testImplementation(platform("org.junit:junit-bom:5.8.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation(kotlin("stdlib-jdk8"))
}

kotlin {
    jvmToolchain(20) // Use Java 17 or higher
}

// https://github.com/sauravjha/kotlin-application-multiple-test-config/blob/master/build.gradle.kts
tasks {
    test {
        useJUnitPlatform()

        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}

            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                val suiteName = suite.name
                println(suiteName)
                if (suiteName.contains("Test Executor")) { // root suite
                    println(
                        "Test summary: ${result.testCount} tests, " +
                                "${result.successfulTestCount} succeeded, " +
                                "${result.failedTestCount} failed, " +
                                "${result.skippedTestCount} skipped"
                    )

                }
            }
        })
    }
}
