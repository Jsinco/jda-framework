plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "8.3.5"
}

group = "dev.jsinco.discord"
version = "1.7"

repositories {
    mavenCentral()
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
}

dependencies {
    compileOnly("net.dv8tion:JDA:5.0.0-beta.24") {
        exclude("org.slf4j", "slf4j-api")
    }
    compileOnly("club.minnced:discord-webhooks:0.8.4") {
        exclude("org.slf4j", "slf4j-api")
    }
    // Logger
    compileOnly("org.apache.logging.log4j:log4j-core:2.24.1")
    compileOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.24.1")
    compileOnly("org.apache.logging.log4j:log4j-api:2.24.1")

    // Jetbrains Annotations
    implementation("org.jetbrains:annotations:25.0.0")

    // Google guava/gson
    implementation("com.google.guava:guava:33.3.1-jre")
    implementation("com.google.code.gson:gson:2.10.1")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:5.0.5")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.jar {
    archiveClassifier.set("original")
}

tasks.shadowJar {
    dependencies {
        // Include all
    }
    archiveClassifier.set("")
}

tasks.build {
    dependsOn("shadowJar")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}

publishing {
    val user = System.getenv("repo_username")
    val pass = System.getenv("repo_secret")

    repositories {
        if (user != null && pass != null) {
            maven {
                name = "jsinco-repo"
                url = uri("https://repo.jsinco.dev/releases")
                credentials(PasswordCredentials::class) {
                    username = user
                    password = pass
                }
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }

    publications {
        if (user != null && pass != null) {
            create<MavenPublication>("maven") {
                groupId = "dev.jsinco.discord"
                artifactId = "jda-framework"
                version = project.version.toString()
                artifact(tasks.shadowJar.get().archiveFile) {
                    builtBy(tasks.shadowJar)
                }
            }
        }
    }
}
