package com.auth.service

import com.auth.authority.JwtTokenProvider
import com.auth.dto.MemberDtoRequest
import com.auth.dto.MemberLoginDtoRequest
import com.auth.dto.TokenInfo
import com.auth.entity.Member
import com.auth.entity.MemberRole
import com.auth.repository.MemberRepository
import com.auth.repository.MemberRoleRepository
import com.auth.status.ROLE
import com.common.exception.InvalidInputException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {

    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        // email 중복 확인
        var member: Member? = memberRepository.findByEmail(memberDtoRequest.email)
        if (member != null) {
            throw InvalidInputException("이미 가입된 이메일입니다.")
        }

        member = memberDtoRequest.toEntity()
        memberRepository.save(member)

        val memberRole = MemberRole(
            null,
            ROLE.MEMBER,
            member
        )
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    /**
     * 로그인
     */
    fun login(memberLoginDtoRequest: MemberLoginDtoRequest): String {

        val encoder = BCryptPasswordEncoder()

        val member: Member = memberRepository.findByEmail(memberLoginDtoRequest.email)
            ?: throw BadCredentialsException("가입되지 않은 이메일입니다.")

        val isMatched = encoder.matches(memberLoginDtoRequest.password, member.password)
        if (!isMatched) {
            throw BadCredentialsException("비밀번호가 일치하지 않습니다.")
        }

        return jwtTokenProvider.createToken(member)
    }
}