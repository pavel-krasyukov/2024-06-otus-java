import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    //id("application")
    id("java")
    id ("com.github.johnrengelman.shadow")
}


dependencies {
    implementation ("com.google.guava:guava")
}

val mainClassName = "ru.otus.HelloOtus"

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("gradleHelloOtus")
        archiveVersion.set("0.1")
        archiveClassifier.set("pavel")
        manifest {
            attributes(mapOf("Main-Class" to mainClassName))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}


