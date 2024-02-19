package com.auth.authority

import com.common.dto.ErrorResponse
import com.common.status.ErrorCode
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

class JwtSecurityFilter(
    private val jwtTokenProvider: JwtTokenProvider
): GenericFilterBean() {
    private val log = KotlinLogging.logger {}

    override fun doFilter(
        req: ServletRequest,
        res: ServletResponse,
        chain: FilterChain?,
    ) {

        val token: String? = getToken(req as HttpServletRequest)

        try {
            // 토큰이 존재할때만 검사
            if ( token != null) {
                val authentication = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
            // 토큰 검사를 안할땐 그냥 통과
            chain?.doFilter(req, res)

        } catch (e: Exception) {
            log.error {"Token Error: $e"}
            when (e) {
                is SecurityException -> {setErrorResponse(res as HttpServletResponse, ErrorCode.INVALID_TOKEN)}
                is ExpiredJwtException -> {setErrorResponse(res as HttpServletResponse, ErrorCode.TOKEN_EXPIRED)}
                is MalformedJwtException -> {setErrorResponse(res as HttpServletResponse, ErrorCode.INVALID_TOKEN)}
                is UnsupportedJwtException -> {setErrorResponse(res as HttpServletResponse, ErrorCode.UNSUPPORTED_TOKEN)}
                is IllegalArgumentException -> {setErrorResponse(res as HttpServletResponse, ErrorCode.INVALID_TOKEN)}
                is JwtException -> {setErrorResponse(res as HttpServletResponse, ErrorCode.INVALID_TOKEN)}
                else -> {setErrorResponse(res as HttpServletResponse, ErrorCode.INVALID_TOKEN)}
            }
        }
    }

    private fun getToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        log.info{ "Bearer Token: $bearerToken"}
        return if (StringUtils.hasText(bearerToken) &&
            bearerToken.startsWith("Bearer")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        val objectMapper = ObjectMapper()
        response.status = errorCode.code
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        try {
            response.writer.write(
                objectMapper.writeValueAsString(
                    ErrorResponse(errorCode.code, errorCode.msg)
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
