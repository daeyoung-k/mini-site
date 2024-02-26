package com.auth.config

import com.auth.authority.JwtSecurityFilter
import com.auth.authority.JwtTokenProvider
import com.auth.service.Oauth2UserDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class JwtSecurityConfiguration(
    private val jwtTokenProvider: JwtTokenProvider,
    private val oauth2UserDetailService: Oauth2UserDetailService
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
//            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.NEVER) }
            .anonymous{ it.disable() }
            .oauth2Login { it ->
                it.authorizationEndpoint {
                    it.baseUri("/oauth2/authorization")

                }
//                it.redirectionEndpoint { it.baseUri("/oauth2/callback/*") }
                it.userInfoEndpoint { it.userService(oauth2UserDetailService) }
            }
//            .authorizeHttpRequests {
//                it.requestMatchers(
//                    "/api/member/sign-up",
//                    "/api/member/login",
//                    "/oauth2/authorization"
//                    ).permitAll()
//                    .requestMatchers("/api/**").hasAnyRole("MEMBER", "ADMIN")
//            }
//            .addFilterBefore(
////                JwtSecurityFilter(jwtTokenProvider),
//                UsernamePasswordAuthenticationFilter::class.java
//            )

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}