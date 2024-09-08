import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm")
    id("com.google.protobuf") version "0.9.4"
}

dependencies {
    // grpc
    api("io.grpc:grpc-protobuf:1.66.0")
    api("io.grpc:grpc-kotlin-stub:1.4.1")
    api("io.grpc:grpc-netty-shaded:1.66.0")
    api("io.grpc:grpc-core:1.66.0")
    api("com.google.protobuf:protobuf-kotlin:3.25.1")

    // swagger
    api("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.66.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}