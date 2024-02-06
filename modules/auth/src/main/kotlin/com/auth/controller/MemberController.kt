package com.auth.controller

import com.auth.dto.MemberDtoRequest
import com.auth.dto.MemberLoginDtoRequest
import com.auth.dto.TokenInfo
import com.auth.service.MemberService
import com.common.dto.BaseResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid memberLoginDtoRequest: MemberLoginDtoRequest): BaseResponse<TokenInfo> {
        val tokenInfo = memberService.login(memberLoginDtoRequest)
        return BaseResponse(data = tokenInfo)
    }
}