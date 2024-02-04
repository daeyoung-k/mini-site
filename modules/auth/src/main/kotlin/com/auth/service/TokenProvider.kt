package com.auth.service

import com.nimbusds.jose.util.StandardCharset
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service
import java.security.Key
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@PropertySource("classpath:jwt.yml")
@Service
class TokenProvider(
    @Value("\${secret-key}")
    private val secretKey: String,
    @Value("\${expiration-hours}")
    private val expirationHours: Long,
    @Value("\${issuer}")
    private val issuer: String,

) {
    val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharset.UTF_8))
    fun createToken(userSpec: String): String {
        val token = Jwts.builder()
            .signWith(key)
            .issuer(issuer)
            .issuedAt(Timestamp.valueOf(LocalDateTime.now()))
            .expiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))
            .subject(userSpec)
            .compact()
        return token
    }

    fun decodeJwt(
        jwtToken: String,
        ): Claims {
        return Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jwtToken)
            .body
    }

}