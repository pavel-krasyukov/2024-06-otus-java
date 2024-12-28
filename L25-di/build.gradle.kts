

dependencies {
    implementation("org.reflections:reflections:0.10.2")
    //непонятный глюк, но если прописываю завимисоть так implementation("org.reflections:reflections") -
    // не работает import org.reflections.Reflections;
    implementation("ch.qos.logback:logback-classic")

    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}