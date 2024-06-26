package com.auth.controller

import com.auth.dto.MemberLoginDtoRequest
import com.auth.repository.MemberRepository
import com.common.dto.BaseResponse
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member")
@RestController
class MemberController(
    private val memberRepository: MemberRepository
) {

    /**
     * 회원가입
     */
//    @PostMapping("/sign-up")
//    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
//        val resultMsg: String = memberService.signUp(memberDtoRequest)
//        return BaseResponse(message = resultMsg)
//    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    fun login(
        response: HttpServletResponse,
        @RequestBody @Valid memberLoginDtoRequest: MemberLoginDtoRequest
    ): BaseResponse<String> {
        return BaseResponse()
    }

}