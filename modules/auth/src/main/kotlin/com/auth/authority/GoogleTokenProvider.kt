package com.auth.authority

import com.google.api.client.json.JsonFactory
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import org.springframework.stereotype.Component

@Component
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