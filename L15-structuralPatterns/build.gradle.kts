plugins {
    id("java")
}

group = "java.ru.otus"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Зависимость для Mockito
    testImplementation("org.mockito:mockito-core:4.0.0") // или более новая версия, если она доступна
    // Зависимость для AssertJ
    testImplementation("org.assertj:assertj-core:3.21.0") // или более новая версия

    implementation ("ch.qos.logback:logback-classic")
}

tasks.test {
    useJUnitPlatform()
}

/*
* dependencies {
    implementation ("ch.qos.logback:logback-classic")

    testImplementation ("org.junit.jupiter:junit-jupiter-api")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine")
    testImplementation ("org.junit.jupiter:junit-jupiter-params")
    testImplementation ("org.assertj:assertj-core")
    testImplementation ("org.mockito:mockito-core")
    testImplementation ("org.mockito:mockito-junit-jupiter")
}
* */