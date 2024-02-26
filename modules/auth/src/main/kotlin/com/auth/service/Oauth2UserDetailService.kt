package com.auth.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class Oauth2UserDetailService: OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val attributes = mapOf(
            "sub" to "1234567890",
            "name" to "John Doe",
            "email" to "john.doe@example.com"
            // 다른 사용자 정보 추가 가능
        )
        val authorities = listOf(OAuth2UserAuthority(attributes))
        return DefaultOAuth2User(authorities, attributes, "sub")
    }
}