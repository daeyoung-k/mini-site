package com.auth.service

import com.auth.entity.Member
import com.auth.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
): UserDetailsService{
    override fun loadUserByUsername(email: String): UserDetails =
        memberRepository.findByEmail(email)
            ?.let { createUserDetails(it) }
            ?: throw UsernameNotFoundException("$email 의 유저를 찾을수 없습니다.")


    private fun createUserDetails(member: Member): UserDetails {
        val user = User(
            member.email,
            passwordEncoder.encode(member.password),
            member.roles!!.map { SimpleGrantedAuthority("ROLE_${it.role}") }
        )
        return user

    }

}