package com.wanted.common

data class ApiResponse<T>(
    val status: ResultType,
    val message: String? = null,
    val data: T? = null
) {
    companion object {
        fun <T> success(message: String? = null, data: T? = null): ApiResponse<T> =
            ApiResponse(ResultType.SUCCESS, message, data)

        fun <T> success(data: T): ApiResponse<T> =
            ApiResponse(ResultType.SUCCESS, data = data)

        fun error(message: String): ApiResponse<Nothing> =
            ApiResponse(ResultType.ERROR, message)

        fun <T> error(data: T): ApiResponse<T> =
            ApiResponse(ResultType.ERROR, null, data)
    }
}

enum class ResultType {
    SUCCESS, ERROR
}