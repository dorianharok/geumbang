package com.wanted.authserver.grpc

import com.wanted.authserver.jwt.JwtProvider
import com.wanted.authserver.exception.TokenExpiredException
import com.wanted.authserver.exception.TokenInvalidException
import com.wanted.authserver.storage.UserRepository
import com.wanted.common.grpc.AuthServiceGrpc
import com.wanted.common.grpc.User
import com.wanted.common.grpc.ValidateTokenRequest
import com.wanted.common.grpc.ValidateTokenResponse
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Service
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull

@Service
@GrpcService
class AuthServiceImpl(
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository
) : AuthServiceGrpc.AuthServiceImplBase() {

    override fun validateToken(request: ValidateTokenRequest, responseObserver: StreamObserver<ValidateTokenResponse>) {
        val token = request.token

        try {
            val isValid = jwtProvider.validateToken(token)
            val user = if (isValid) getUserFromToken(token) else null

            val response = ValidateTokenResponse.newBuilder()
                .setIsValid(isValid)
                .apply { user?.let { setUser(it) } }
                .build()

            responseObserver.onNext(response)
        } catch (e: TokenExpiredException) {
            val response = ValidateTokenResponse.newBuilder()
                .setIsValid(false)
                .build()
            responseObserver.onNext(response)
        } catch (e: TokenInvalidException) {
            val response = ValidateTokenResponse.newBuilder()
                .setIsValid(false)
                .build()
            responseObserver.onNext(response)
        } catch (e: Exception) {
            val response = ValidateTokenResponse.newBuilder()
                .setIsValid(false)
                .build()
            responseObserver.onNext(response)
        }

        responseObserver.onCompleted()
    }

    private fun getUserFromToken(token: String): User? {
        val userId = jwtProvider.getIdFromToken(token)
        return userRepository.findByIdOrNull(userId)?.let { user ->
            User.newBuilder()
                .setUserId(user.id.toString())
                .setUsername(user.username)
                .build()
        }
    }
}