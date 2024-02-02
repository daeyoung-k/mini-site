package com.auth.service

import io.jsonwebtoken.Jwts

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.crypto.spec.SecretKeySpec

@PropertySource("classpath:jwt.yml")
@Service
class TokenProvider(
    @Value("\${secret-key}")
    private val secretKey: String,
    @Value("\${expiration-hours}")
    private val expirationHours: Long,
    @Value("\${issuer}")
    private val issuer: String
) {
    fun createToken(userSpec: String) = Jwts.builder()
        .issuer(issuer)
        .issuedAt(Timestamp.valueOf(LocalDateTime.now()))
        .expiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))
        .subject(userSpec)
        .compact()!!
}