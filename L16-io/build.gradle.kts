plugins {
    id("java")
}

group = "ru.otus"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.google.guava:guava")
    testImplementation ("org.assertj:assertj-core")
    implementation ("jakarta.json:jakarta.json-api:2.0.0")
    implementation ("org.glassfish:jakarta.json:2.0.0")
}

tasks.test {
    useJUnitPlatform()
}