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
    implementation("ch.qos.logback:logback-classic")
    implementation("org.flywaydb:flyway-core:8.5.11")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework:spring-core")
    implementation("com.zaxxer:HikariCP")
}

tasks.test {
    useJUnitPlatform()
}