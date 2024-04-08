package com.auth.service.oauth2

import com.auth.domain.Member
import com.auth.dto.oauth2.GoogleResponse
import com.auth.dto.oauth2.KakaoResponse
import com.auth.dto.oauth2.NaverResponse
import com.auth.dto.oauth2.OAuth2Response
import com.auth.repository.MemberRepository
import com.auth.status.jwt.ProviderType
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomOAuth2UserService(
    private val memberRepository: MemberRepository
): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User? {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User: OAuth2User = delegate.loadUser(userRequest)

        val provider = userRequest.clientRegistration.registrationId
//
        val oAuth2Response: OAuth2Response = when (provider) {
            "google" -> {
                GoogleResponse(oAuth2User.attributes as MutableMap<String, Objects>)
            }

            "naver" -> {
                NaverResponse(oAuth2User.attributes["response"] as MutableMap<String, Objects>)
            }

            "kakao" -> {
                KakaoResponse(oAuth2User.attributes as MutableMap<String, Objects>)
            }

            else -> return null
        }

        println("oAuth2User.attributes: ${oAuth2User.attributes}")

        println("oAuth2Response: $oAuth2Response")
        println("oAuth2Response: ${oAuth2Response.getProvider()}")
        println("oAuth2Response: ${oAuth2Response.getProviderId()}")
        println("oAuth2Response: ${oAuth2Response.getEmail()}")
        println("oAuth2Response: ${oAuth2Response.getName()}")

        val user_email: String = oAuth2Response.getEmail()

        var user_data: Member? = memberRepository.findByEmail(user_email)
        println("user_data: $user_data")
        println(oAuth2Response.getProvider() as ProviderType)
        if (user_data == null) {
            val member = Member(
                email = user_email,
                name = oAuth2Response.getName(),
                provider = oAuth2Response.getProvider() as ProviderType,
            )

            println("member: $member")
        }

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
        return null
    }
}