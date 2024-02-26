package com.auth.authority

import org.springframework.stereotype.Component

//@Component
class GoogleTokenProvider {

    fun googleIdToken(token: String): String {
//        val httpTransport = NetHttpTransport()
//        val jacksonFactory = JacksonF
//        val verifier: GoogleIdTokenVerifier = GoogleIdTokenVerifier.Builder(httpTransport, jacksonFactory)
//            .setAudience(listOf("YOUR_CLIENT_ID"))
//            .build()
        return token
    }
}