package com.auth.dto

data class JwtTokenDto(
    val accessToken: String,
    val refreshToken: String,
)
