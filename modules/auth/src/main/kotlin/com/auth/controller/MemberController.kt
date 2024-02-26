package com.auth.controller

import com.auth.dto.MemberDtoRequest
import com.auth.dto.MemberLoginDtoRequest
import com.auth.dto.TokenInfo
import com.auth.service.MemberService
import com.common.dto.BaseResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member", "/oauth2")
@RestController
class MemberController(
    private val memberService: MemberService
) {

    private val logger = KotlinLogging.logger {}

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    fun login(
        response: HttpServletResponse,
        @RequestBody @Valid memberLoginDtoRequest: MemberLoginDtoRequest
    ): BaseResponse<String> {
        val token = memberService.login(memberLoginDtoRequest)
        response.setHeader("Authorization", token)
        return BaseResponse()
    }

    @GetMapping("/provider-login")
    fun providerLogin(@AuthenticationPrincipal oauth2User: OAuth2User): BaseResponse<String> {
        logger.info { "oauth2User: $oauth2User"}
        return BaseResponse()
    }

    @GetMapping("/callback/google")
    fun callback(
        @AuthenticationPrincipal oauth2User: OAuth2User?,
        @RequestParam code: String
    ): String {
        logger.info { "code: $code"}
        logger.info { "oauth2User: $oauth2User"}
        return "ok"
    }

}