package com.wanted.resourceserver.security

import com.wanted.resourceserver.grpc.AuthServiceClient
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtTokenFilter(
    private val authServiceClient: AuthServiceClient
) : OncePerRequestFilter() {

    private val pathMatcher = AntPathMatcher()

    companion object {
        const val TOKEN_PREFIX = "Bearer "
    }
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = extractToken(request)

        if (token != null) {
            val validateResponse = authServiceClient.validateToken(token)
            if(validateResponse.isValid) {
                val user = validateResponse.user
                val loginUser = LoginUser(com.wanted.resourceserver.domain.User(user.userId.toLong(), user.username))
                val authentication = UsernamePasswordAuthenticationToken(loginUser, null, loginUser.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            bearerToken.removePrefix(TOKEN_PREFIX)
        } else null
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        val excludedPaths = listOf(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/static/openapi/**",
            "/openapi/**",
            "/static/**"
        )
        return excludedPaths.any { pathMatcher.match(it, path) }
    }
}