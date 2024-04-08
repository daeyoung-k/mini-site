package com.auth.dto.oauth2

import java.util.*

class GoogleResponse(
    private val attribute: MutableMap<String, Objects>
): OAuth2Response {
    override fun getProvider(): String = "GOOGLE"
    override fun getProviderId(): String = attribute["sub"].toString()
    override fun getEmail(): String = attribute["email"].toString()
    override fun getName(): String = attribute["name"].toString()
}