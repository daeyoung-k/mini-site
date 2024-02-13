package com.auth.authority

import com.common.dto.BaseResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

private val logger = KotlinLogging.logger {}


class JwtSecurityFilter(
    private val jwtTokenProvider: JwtTokenProvider
): GenericFilterBean() {

    override fun doFilter(
        req: ServletRequest?,
        res: ServletResponse?,
        chain: FilterChain?
    ) {
        val token = getToken(req as HttpServletRequest)

        println("token: $token")

        if (token != null) {
            try {
                val authentication = jwtTokenProvider.getAuthentication(token)
                println("authentication: $authentication")
                SecurityContextHolder.getContext().authentication = authentication
                println(SecurityContextHolder.getContext().authentication)
            } catch (e: Exception) {
                // 그 외의 예외가 발생할 경우 처리할 수 있는 방법에 따라 적절히 처리합니다.
                println("예외가 발생했습니다: ${e.message}")
                // 그 외의 예외를 다시 던집니다.
                throw ServletException("ㅁㄴㅇㅁㄴㅇ")
            }
        }
        chain?.doFilter(req, res)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) &&
            bearerToken.startsWith("Bearer")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}
