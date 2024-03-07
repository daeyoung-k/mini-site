package com.common.dto.oauth2

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Objects

class NaverResponse(

    @JsonProperty("response") val attribute: MutableMap<String, Objects>
): OAuth2Response{
    override fun getProvider(): String = "naver"
    override fun getProviderId(): String = attribute["id"].toString()
    override fun getEmail(): String = attribute["email"].toString()
    override fun getName(): String = attribute["name"].toString()
}