package com.auth.dto.oauth2

interface OAuth2Response {

    fun getProvider(): String

    fun getProviderId(): String

    fun getEmail(): String

    fun getName(): String
}