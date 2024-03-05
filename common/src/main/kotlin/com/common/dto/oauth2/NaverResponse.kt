package com.common.dto.oauth2

class NaverResponse(
    private val attribute: MutableMap<String, Any>
): OAuth2Response{
    override fun getProvider(): String = "naver"
    override fun getProviderId(): String = attribute["id"].toString()
    override fun getEmail(): String = attribute["email"].toString()
    override fun getName(): String = attribute["name"].toString()
}