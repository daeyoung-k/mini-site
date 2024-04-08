package com.auth.dto.oauth2

import java.util.*

class KakaoResponse(
    private val attribute: MutableMap<String, Objects>
): OAuth2Response {

    override fun getProvider(): String = "KAKAO"

    override fun getProviderId(): String = attribute["id"].toString()

    override fun getEmail(): String {
        val account = attribute["kakao_account"] as MutableMap<String, Any>
        return account["email"].toString()
    }

    override fun getName(): String {
        val profile = attribute["properties"] as MutableMap<String, Any>
        return profile["nickname"].toString()
    }
}