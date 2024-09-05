package com.wanted.authserver.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.wanted.common.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {

    private val objectMapper = ObjectMapper()
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val errorMessage = authException.message ?: "Unauthorized access"
        val errorResponse = ApiResponse.error(errorMessage)
        val jsonResponse = objectMapper.writeValueAsString(errorResponse)

        response.writer.write(jsonResponse)
    }
}