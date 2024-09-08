package com.wanted.authserver.api.v1.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.wanted.authserver.domain.TokenPair
import io.swagger.v3.oas.annotations.media.Schema

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TokenResponse(
    @Schema(description = "Access Token")
    val accessToken: String,
    @Schema(description = "Refresh Token")
    val refreshToken: String,
) {
    constructor(token: TokenPair): this(token.accessToken, token.refreshToken)
}
