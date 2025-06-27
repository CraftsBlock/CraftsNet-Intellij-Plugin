import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.intellij.platform)
}

val pluginVersion: String by project
val ideaVersionName: String by project

group = "de.craftsblock.craftsnet"
version = "$ideaVersionName-$pluginVersion"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create(
            IntelliJPlatformType.IntellijIdeaCommunity,
            libs.versions.intellij.ide,
            useInstaller = false
        )

        bundledPlugin("com.intellij.java")
        bundledPlugin("com.intellij.modules.json")

        pluginVerifier()
    }
}

intellijPlatform {

    pluginConfiguration {
        version.set(project.version.toString())

        val changelog = File(projectDir, "changelog.html")
        val changelogText = changelog.readText().replace(Regex("<!--.*?-->", RegexOption.DOT_MATCHES_ALL), "")
            .replace(Regex("\\s+"), " ")
            .replace(Regex("\n+"), "")
            .trim()
        changeNotes.set(changelogText)

        ideaVersion {
            sinceBuild = libs.versions.intellij.since
            untilBuild = libs.versions.intellij.until
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
