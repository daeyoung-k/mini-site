package com.auth.service

import com.auth.dto.SignIn
import org.springframework.stereotype.Service

@Service
class Sign(
    private val tokenProvider: TokenProvider
){

    fun signIn(signIn: SignIn): String {
        println(tokenProvider.createToken(signIn.name!!))
        return "token"
    }
}