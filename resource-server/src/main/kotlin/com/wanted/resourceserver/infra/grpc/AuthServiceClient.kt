package com.wanted.resourceserver.infra.grpc

import com.wanted.common.grpc.AuthServiceGrpc
import com.wanted.common.grpc.ValidateTokenRequest
import com.wanted.common.grpc.ValidateTokenResponse
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class AuthServiceClient(private val authServerHost: String, private val authServerPort: Int) {
    private val channel = ManagedChannelBuilder.forAddress(authServerHost, authServerPort)
        .usePlaintext()
        .build()

    private val stub = AuthServiceGrpc.newBlockingStub(channel)
        .withDeadlineAfter(5, TimeUnit.SECONDS)

    fun validateToken(token: String): ValidateTokenResponse {
        val request = ValidateTokenRequest.newBuilder().setToken(token).build()
        return stub.validateToken(request)
    }
}