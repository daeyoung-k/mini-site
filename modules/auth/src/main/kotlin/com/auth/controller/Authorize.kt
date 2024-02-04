package com.auth.controller

import com.auth.dto.SignIn
import com.auth.service.Sign
import com.auth.service.TokenProvider
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Authorize(
    private val sign: Sign,
    private val tokenProvider: TokenProvider
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody signIn: SignIn): String {
        println(signIn)
        val token = sign.signIn(signIn)
        return token
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody jwt: jwtTest): String {
        val auth = tokenProvider.decodeJwt(jwt.jwt)
        return auth.issuer
    }
}


data class jwtTest(
    val jwt: String
)
