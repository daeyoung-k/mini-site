package com.common.dto.oauth2

class GoogleResponse(
    private val attribute: MutableMap<String, Any>
): OAuth2Response {
    override fun getProvider(): String = "google"
    override fun getProviderId(): String = attribute["sub"].toString()
    override fun getEmail(): String = attribute["email"].toString()
    override fun getName(): String = attribute["name"].toString()
}