plugins {
    id("java")
}

group = "ru.otus"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    dependencies {
        implementation ("org.projectlombok:lombok")
        annotationProcessor ("org.projectlombok:lombok")

        implementation("ch.qos.logback:logback-classic")
        implementation("org.hibernate.orm:hibernate-core")
        implementation("org.flywaydb:flyway-core:8.5.11")

        implementation("org.postgresql:postgresql")

        testImplementation("org.junit.jupiter:junit-jupiter-engine")
        testImplementation("org.junit.jupiter:junit-jupiter-params")
        testImplementation("org.assertj:assertj-core")
        testImplementation("org.mockito:mockito-junit-jupiter")

        testImplementation("org.testcontainers:junit-jupiter")
        testImplementation("org.testcontainers:postgresql")
        implementation("com.google.code.gson:gson")

        implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet")
        implementation("org.eclipse.jetty:jetty-server")
        implementation("org.eclipse.jetty.ee10:jetty-ee10-webapp")
        implementation("org.eclipse.jetty:jetty-security")
        implementation("org.eclipse.jetty:jetty-http")
        implementation("org.eclipse.jetty:jetty-io")
        implementation("org.eclipse.jetty:jetty-util")
        implementation("org.freemarker:freemarker")
        implementation("javax.servlet:jstl:1.2")

        /*implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-jetty")*/


    }

}


tasks.test {
    useJUnitPlatform()
}