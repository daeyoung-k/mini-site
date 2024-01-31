package com.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
class BasicAuthSecurityConfiguration {

    @Bean
    fun userDetailsService(): UserDetailsService {
        val users: User.UserBuilder = User.withDefaultPasswordEncoder()
        val manager = InMemoryUserDetailsManager()
        manager.createUser(users.username("user").password("password").roles("USER").build())
        manager.createUser(users.username("admin").password("dummy").roles("ADMIN").build())
        return manager
    }

    @Order(1)
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize("/api/todo", permitAll)     // 특정 url만 오픈
//                authorize(anyRequest, permitAll)    // 모든 요청에 허용
//                authorize(anyRequest, denyAll)    // 모든 요청에 거부
                authorize(anyRequest, authenticated)
            }
            csrf { disable() }   // csrf 토큰 사용안함
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            httpBasic { }
        }
        return http.build()
    }


}