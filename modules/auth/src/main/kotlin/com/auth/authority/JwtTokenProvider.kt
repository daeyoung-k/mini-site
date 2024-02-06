package com.auth.authority

import com.auth.dto.TokenInfo
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.Date

const val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 60 * 24

private val logger = KotlinLogging.logger {}

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret-key}")
    lateinit var secretKey: String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    fun createToken(authentication: Authentication): TokenInfo {
        val authorities: String = authentication
            .authorities
            .joinToString (",", transform = {it.authority} )

        val now = Date()
        val accessException = Date(now.time + EXPIRATION_MILLISECONDS)

        val accessToken = Jwts.builder()
            .subject(authentication.name)
            .claim("auth", authorities)
            .issuedAt(now)
            .expiration(accessException)
            .signWith(key)
            .compact()

        return TokenInfo("Bearer", accessToken)
    }
}