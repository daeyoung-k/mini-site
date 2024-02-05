package com.auth.service

//import com.nimbusds.jose.util.StandardCharset
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

const val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 60 * 24

@Service
class TokenProvider(
//    @Value("\${jwt.secret-key}")
//    private val secretKey: String

) {
//    val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharset.UTF_8))
//    fun createToken(userSpec: String): String {
//        val token = Jwts.builder()
//            .signWith(key)
//            .issuedAt(Timestamp.valueOf(LocalDateTime.now()))
//            .expiration(Date.from(Instant.now().plus(EXPIRATION_MILLISECONDS, ChronoUnit.HOURS)))
//            .subject(userSpec)
//            .compact()
//        return token
//    }
//
//    fun decodeJwt(
//        jwtToken: String,
//        ): Claims {
//        return Jwts.parser()
//            .setSigningKey(key)
//            .build()
//            .parseClaimsJws(jwtToken)
//            .body
//    }

}