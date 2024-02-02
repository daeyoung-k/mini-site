package com.auth.controller

import com.auth.dto.SignIn
import com.auth.service.Sign
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Authorize(
    private val sign: Sign
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody signIn: SignIn): String {
        println(signIn)
        val token = sign.signIn(signIn)
        return token
    }
}