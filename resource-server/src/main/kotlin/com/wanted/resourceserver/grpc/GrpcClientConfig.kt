package com.wanted.resourceserver.grpc

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcClientConfig {

    @Bean
    fun authServiceClient(
        @Value("\${grpc.auth.host}") host: String,
        @Value("\${grpc.auth.port}") port: Int
    ): AuthServiceClient {
        return AuthServiceClient(host, port)
    }
}