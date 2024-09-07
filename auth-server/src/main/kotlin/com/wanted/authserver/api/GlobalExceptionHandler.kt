package com.wanted.authserver.api

import com.wanted.authserver.exception.AuthException
import com.wanted.common.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(AuthException::class)
    fun handleAuthException(e: AuthException): ResponseEntity<ApiResponse<Nothing>> {
        log.warn("AuthException : {}", e.message, e)

        return ResponseEntity.status(e.httpStatus).body(ApiResponse.error(e.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleInvalidRequestBodyException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<List<String>>> {
        val allErrors = e.fieldErrors.map { it.defaultMessage!! }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(allErrors))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("An unexpected error has occurred."))
    }
}