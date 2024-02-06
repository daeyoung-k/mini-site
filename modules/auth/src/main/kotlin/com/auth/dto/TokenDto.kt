package com.auth.dto

data class TokenInfo(
    val grantType: String,
    val accessToken: String,
)
