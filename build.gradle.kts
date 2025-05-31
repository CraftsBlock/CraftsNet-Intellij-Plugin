import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.10"
    id("org.jetbrains.intellij.platform") version "2.6.0"
//    id("org.jetbrains.intellij.platform.migration") version "2.6.0"
}

val intellijVersion = "2025.1.1"
val intellijType = IntelliJPlatformType.IntellijIdeaCommunity

group = "de.craftsblock.craftsnet"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create(intellijType, intellijVersion)

        bundledPlugin("com.intellij.java")
        bundledPlugin("com.intellij.modules.json")

        pluginVerifier()
    }
}

intellijPlatform {

    pluginConfiguration {
        version.set(project.version.toString())

        ideaVersion {
            sinceBuild = "251"
            untilBuild = "252.*"
        }
    }

    signing {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishing {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    pluginVerification {
        ides {
            recommended()
        }
    }

}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
