dependencies {
    /*implementation ("com.google.code.findbugs:jsr305:3.0.2")

    implementation("org.webjars:webjars-locator-core")
    implementation("org.webjars:sockjs-client:1.5.1")
    implementation("org.webjars:stomp-websocket:2.3.4")
    implementation("org.webjars:bootstrap:5.2.3")*/
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    //implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation ("com.google.code.findbugs:jsr305:3.0.2")

    implementation("io.r2dbc:r2dbc-postgresql:0.8.13.RELEASE")
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core") //implementation("org.flywaydb:flyway-core:8.5.11")
    //implementation("io.r2dbc:r2dbc-pool")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
}