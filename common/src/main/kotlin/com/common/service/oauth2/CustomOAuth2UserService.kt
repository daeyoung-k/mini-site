package com.common.service.oauth2

import com.common.dto.oauth2.GoogleResponse
import com.common.dto.oauth2.NaverResponse
import com.common.dto.oauth2.OAuth2Response
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User? {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User: OAuth2User = delegate.loadUser(userRequest)
        println("oAuth2User: $oAuth2User")
        println("oAuth2User attributes: ${oAuth2User.attributes}")
        println("oAuth2User Name: ${oAuth2User.name}")
        println("oAuth2User attributes.response: ${oAuth2User.attributes["response"]}")

        val provider = userRequest.clientRegistration.registrationId

        val oAuth2Response: OAuth2Response = when (provider) {
            "google" -> {
                GoogleResponse(oAuth2User.attributes as MutableMap<String, Any>)
            }

            "naver" -> {
                NaverResponse(oAuth2User.name as MutableMap<String, Any>)
            }

            else -> return null
        }

        println("oAuth2Response: $oAuth2Response")
        println("oAuth2Response: ${oAuth2Response.getProvider()}")
        println("oAuth2Response: ${oAuth2Response.getProviderId()}")
        println("oAuth2Response: ${oAuth2Response.getEmail()}")
        println("oAuth2Response: ${oAuth2Response.getName()}")

        return null
//
//        val userName = "${oAuth2Response.getProvider()}${oAuth2Response.getProviderId()}"
//
//        var existData: UserEntity? = userRepository.findByUsername(userName)
//
//        if (existData == null) {
//            val userEntity = UserEntity(
//                username = userName,
//                name = oAuth2Response.getName(),
//                email = oAuth2Response.getEmail(),
//                role = "ROLE_USER"
//            )
//            userRepository.save(userEntity)
//
//            val userDTO = UserDTO(
//                username = userName,
//                name = oAuth2Response.getName(),
//                role = "ROLE_USER"
//            )
//            return CustomOAuth2User(userDTO)
//        } else {
//
//            existData.email = oAuth2Response.getEmail()
//            existData.name = oAuth2Response.getName()
//
//            userRepository.save(existData)
//
//            val userDTO = UserDTO(
//                username = existData.username,
//                name = existData.name,
//                role = existData.role
//            )
//            return CustomOAuth2User(userDTO)
//        }
    }
}