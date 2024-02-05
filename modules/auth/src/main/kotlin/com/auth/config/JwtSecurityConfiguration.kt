package com.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType

import javax.sql.DataSource


//@Configuration
//@EnableWebSecurity
class JwtSecurityConfiguration {

//    @Bean
//    fun dataSource(): DataSource {
//        return EmbeddedDatabaseBuilder()
//            .setType(EmbeddedDatabaseType.H2)
//            .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//            .build()
//    }
//
//    @Bean
//    fun userDetailsService(
//        dataSource: DataSource
//    ): UserDetailsService {
//        val users: User.UserBuilder = User.withDefaultPasswordEncoder()
////        val manager = InMemoryUserDetailsManager()
//        val manager = JdbcUserDetailsManager(dataSource)
//        manager.createUser(users.username("user").password("password").roles("USER").build())
//        manager.createUser(users.username("admin").password("dummy").roles("ADMIN").build())
//        return manager
//    }
//
//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http {
//            authorizeRequests {
//                authorize("/api/todo", permitAll)     // 특정 url만 오픈
////                authorize(anyRequest, permitAll)    // 모든 요청에 허용
////                authorize(anyRequest, denyAll)    // 모든 요청에 거부
//                authorize(anyRequest, authenticated)
//            }
//            csrf { disable() }   // csrf 토큰 사용안함
//            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
//            httpBasic { }
//            headers { frameOptions { disable() } }      // h2-console 사용하기 위해 설정 (에플리케이션 프레임 허용)
//
////            oauth2ResourceServer {
////                jwt {
////                    jwkSetUri = "https://idp.example.com/.well-known/jwks.json"
////                }
////            }
//        }
//        return http.build()
//    }
//
////    @Bean
////    fun jwtDecoder(): JwtDecoder {
////        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build()
////    }
//
////    @Bean
////    fun jwtEncoder(): JwtEncoder {
////
////    }


}