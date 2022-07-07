///////////////////////////////////////////////////////////////////////////
// Versions
///////////////////////////////////////////////////////////////////////////

val wrappersBom = "1.0.0-pre.340"
val kotlinxCoroutines = "1.6.0"
val kotlinxSerialization = "1.3.2"

///////////////////////////////////////////////////////////////////////////
// Settings
///////////////////////////////////////////////////////////////////////////

plugins {
    kotlin("js") version "1.7.10"
    kotlin("plugin.serialization") version "1.6.21"
}

group = "site.vie10"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
}

///////////////////////////////////////////////////////////////////////////
// Dependencies
///////////////////////////////////////////////////////////////////////////

dependencies {
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", kotlinxSerialization)
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", kotlinxCoroutines)
    implementation(enforcedPlatform(kotlinw("wrappers-bom:$wrappersBom")))

    implementation(kotlinw("react"))
    implementation(kotlinw("react-dom"))

    implementation(kotlinw("mui"))
    implementation(kotlinw("mui-icons"))

    implementation(npm("@emotion/react", "11.8.1"))
    implementation(npm("@emotion/styled", "11.8.1"))
    implementation(npm("mui-markdown", "0.5.3"))
    implementation(npm("prism-react-renderer", "1.3.1"))
    implementation(npm("markdown-to-jsx", "7.1.6"))
}

///////////////////////////////////////////////////////////////////////////
// Tasks
///////////////////////////////////////////////////////////////////////////

tasks.wrapper {
    version = "7.4.2"
}

///////////////////////////////////////////////////////////////////////////
// Helpers for beautify previous code
///////////////////////////////////////////////////////////////////////////

fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"
