package com.wanted.resourceserver.support

import java.util.function.Function


data class PaginationResponse<T>(
    val page: Int,
    val totalPages: Int,
    val links: LinksResponse,
    val data: List<T>
) {
    fun <U> map(converter: Function<in T, out U>): PaginationResponse<U> {
        return PaginationResponse(page, totalPages, links, getConvertedContent(converter))
    }

    private fun <U> getConvertedContent(converter: Function<in T, out U>): List<U> {
        return this.data.map { t: T -> converter.apply(t) }
    }
}

data class LinksResponse(
    val self: String,
    val first: String,
    val previous: String?,
    val next: String?,
    val last: String
)
