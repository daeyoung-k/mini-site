package com.auth.authority

import com.auth.dto.TokenInfo
import com.auth.entity.Member
import com.auth.repository.MemberRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.Date

const val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 30

private val logger = KotlinLogging.logger {}

@Component
class JwtTokenProvider(
    private val memberRepository: MemberRepository
) {
    @Value("\${jwt.secret-key}")
    lateinit var secretKey: String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    /**
     * 토큰 생성
     */
    fun createToken(member: Member): TokenInfo {
        val authority: List<String> = member.roles!!.map { "ROLE_${it.role}" }

        val now = Date()
        val accessException = Date(now.time + EXPIRATION_MILLISECONDS)

        val accessToken = Jwts.builder()
            .subject(member.email)
            .claim("auth", authority)
            .issuedAt(now)
            .expiration(accessException)
            .signWith(key)
            .compact()

        return TokenInfo("Bearer", accessToken)
    }

    /**
     * 토큰 정보 추출
     */
    fun getAuthentication(token: String): Authentication {
        val claims: Claims = validateToken(token) ?: throw SecurityException("토큰이 유효하지 않습니다.")
        val member: Member = memberRepository.findByEmail(claims.subject) ?: throw SecurityException("회원정보가 없습니다.")

        val authentication: Authentication = UsernamePasswordAuthenticationToken(
            member.email,
            "",
            claims["auth", List::class.java].map { SimpleGrantedAuthority(it as String) }
        )
        return authentication
    }

    /**
     * 토큰 검증 */
    fun validateToken(token: String): Claims? {
        try {
            return getClaims(token)
        } catch (e: Exception) {
            when (e) {
                is SecurityException -> {}  // Invalid JWT Token
                is MalformedJwtException -> {}  // Invalid JWT Token
                is ExpiredJwtException -> {}    // Expired JWT Token
                is UnsupportedJwtException -> {}
                is IllegalArgumentException -> {}
                else -> {}  // else
            }
            logger.info { "Token Error: $e.message" }
            return null
        }
    }

    /**
     * 토큰 decode
     */
    private fun getClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

}