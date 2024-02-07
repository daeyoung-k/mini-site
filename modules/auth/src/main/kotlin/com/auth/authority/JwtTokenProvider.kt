package com.auth.authority

import com.auth.dto.TokenInfo
import com.auth.entity.Member
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

const val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 5

private val logger = KotlinLogging.logger {}

@Component
class JwtTokenProvider {
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
    fun getAuthentication(token: String): String {

        val claims: Claims = getClaims(token)

//            val auth = claims["auth"] ?: throw SignatureException("잘못된 토큰입니다.")

        logger.info { "claims : $claims" }
        logger.info { "date : ${Date()}" }

        return "string"


    }

    private fun getClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

}