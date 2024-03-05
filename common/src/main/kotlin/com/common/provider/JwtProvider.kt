package com.common.provider

import com.common.dto.JwtToken
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

const val ACCESS_EXPIRATION: Long = 1000 * 60 * 10
const val REFRESH_EXPIRATION: Long = 1000 * 60 * 60 * 24

@Component
class JwtProvider {

    @Value("\${jwt.secret-key}")
    lateinit var secretKey: String
    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    fun getType(token: String): String = getClaims(token)["type"].toString()
    fun getEmail(token: String): String = getClaims(token).subject
    fun getRole(token: String): String = getClaims(token)["role"].toString()

    fun createAccessToken(token: String, role: String): String {
        val now = Date()
        val accessExpiration = Date(now.time + ACCESS_EXPIRATION)

        val refreshToken: Claims = getClaims(token)
        val email = refreshToken.subject

        return Jwts.builder()
            .subject(email)
            .claim("type", "ACCESS")
            .claim("role", role)
            .issuedAt(now)
            .expiration(accessExpiration)
            .signWith(key)
            .compact()
    }

    fun createJwtToken(email: String, role: String): JwtToken {
        val now = Date()
        val accessExpiration = Date(now.time + ACCESS_EXPIRATION)
        val refreshExpiration = Date(now.time + REFRESH_EXPIRATION)

        val accessToken = Jwts.builder()
            .subject(email)
            .claim("type", "ACCESS")
            .claim("role", role)
            .issuedAt(now)
            .expiration(accessExpiration)
            .signWith(key)
            .compact()

        val refreshToken = Jwts.builder()
            .subject(email)
            .claim("type", "REFRESH")
            .issuedAt(now)
            .expiration(refreshExpiration)
            .signWith(key)
            .compact()

        return JwtToken(accessToken, refreshToken)
    }

    fun getClaims(token: String): Claims = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .payload
}