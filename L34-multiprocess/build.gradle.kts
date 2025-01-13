import com.google.protobuf.gradle.*

plugins {
    id("idea")
    id("com.google.protobuf")
}
val errorProneAnnotations: String by project
val tomcatAnnotationsApi: String by project

dependencies {
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    implementation("ch.qos.logback:logback-classic")
    /*implementation("io.grpc:grpc-netty")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")*/
    //Работает только с указанием версий
    implementation("io.grpc:grpc-netty:1.56.0")
    implementation("io.grpc:grpc-protobuf:1.56.0")
    implementation("io.grpc:grpc-stub:1.56.0")
    implementation("io.grpc:protoc-gen-grpc-java:1.69.0")
    implementation("com.google.protobuf:protobuf-java")
    implementation("com.google.errorprone:error_prone_annotations:$errorProneAnnotations")
    implementation("org.apache.tomcat:annotations-api:$tomcatAnnotationsApi")
}

val protoSrcDir = "$projectDir/build/generated"

idea {
    module {
        sourceDirs = sourceDirs.plus(file(protoSrcDir))
    }
}

sourceSets {
    main {
        proto {
            srcDir(protoSrcDir)
        }
    }
}

/*protobuf {
    generatedFilesBaseDir = protoSrcDir

    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }

    generateProtoTasks {
        ofSourceSet("main")
    }
}

afterEvaluate {
    tasks {
        getByName("generateProto").dependsOn(processResources)
    }
}*/

protobuf {
    generatedFilesBaseDir = protoSrcDir

    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.56.1"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

afterEvaluate {
    tasks {
        getByName("generateProto").dependsOn(processResources)
    }
}

