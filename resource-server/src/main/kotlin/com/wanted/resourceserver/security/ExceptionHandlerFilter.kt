package com.wanted.resourceserver.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.wanted.common.ApiResponse
import com.wanted.resourceserver.exception.CoreException
import com.wanted.resourceserver.exception.TokenNotFoundException
import com.wanted.resourceserver.infra.grpc.AuthServiceClient
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ExceptionHandlerFilter: OncePerRequestFilter() {

    private val objectMapper = ObjectMapper()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: CoreException) {
            response.status = e.httpStatus.value()
            response.contentType = MediaType.APPLICATION_JSON_VALUE

            val errorResponse = ApiResponse.error(e.message)
            val jsonResponse = objectMapper.writeValueAsString(errorResponse)

            response.characterEncoding = "UTF-8"
            response.writer.write(jsonResponse)
        }
    }

}