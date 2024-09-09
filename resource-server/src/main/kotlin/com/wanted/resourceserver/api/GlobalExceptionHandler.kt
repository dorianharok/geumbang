package com.wanted.resourceserver.api

import com.wanted.common.ApiResponse
import com.wanted.resourceserver.exception.CoreException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(CoreException::class)
    fun handleAuthException(e: CoreException, request: HttpServletRequest): ResponseEntity<ApiResponse<Nothing>> {
        log.warn("CoreException : {}, URI: {}", e.message, request.requestURL, e)

        return ResponseEntity.status(e.httpStatus).body(ApiResponse.error(e.message))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("An unexpected error has occurred."))
    }
}