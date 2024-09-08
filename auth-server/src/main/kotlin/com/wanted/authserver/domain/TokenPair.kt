package com.wanted.authserver.domain

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)
