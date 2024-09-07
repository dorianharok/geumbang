package com.wanted.authserver.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
) {
    @Value("\${security.jwt.secret}")
    lateinit var secretString: String
    @Value("\${security.jwt.expiration.access}")
    lateinit var accessTokenValidityInMilliseconds: Number
    @Value("\${security.jwt.expiration.refresh}")
    lateinit var refreshTokenValidityInMilliseconds: Number

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return CustomAuthenticationEntryPoint()
    }

    @Bean
    fun jwtProvider() = JwtProvider(secretString, accessTokenValidityInMilliseconds.toLong(), refreshTokenValidityInMilliseconds.toLong())

    @Bean
    fun jwtTokenFilter(): JwtTokenFilter {
        return JwtTokenFilter(jwtProvider())
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint())
            }
            .authorizeHttpRequests { authorize ->
                authorize
//                    .requestMatchers(
//                        "/swagger-ui.html",
//                        "/swagger-ui/**",
//                        "/api-docs/**",
//                        "/swagger-resources/**",
//                        "/webjars/**",
//                        "/static/openapi/**",
//                        "/openapi/**",
//                        "/static/**")
//                    .permitAll()
//                    .requestMatchers(
//                        "/v1/users/join",
//                        "/v1/users/login",
//                        "/v1/users/reissue",
//                        "/test"
//                    )
//                    .permitAll()
//                    .anyRequest().authenticated()
                    .anyRequest().permitAll()
            }
            .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:8080")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}