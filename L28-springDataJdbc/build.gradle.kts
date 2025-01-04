plugins {
    id("java")
}

dependencies {
        implementation ("org.projectlombok:lombok")
        implementation("org.hibernate.orm:hibernate-core")
        implementation("org.flywaydb:flyway-core:8.5.11")

        implementation("org.postgresql:postgresql")

        implementation ("org.springframework.boot:spring-boot-starter-data-jdbc")
        implementation ("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")


}
