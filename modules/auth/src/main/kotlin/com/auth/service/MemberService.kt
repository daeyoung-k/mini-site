package com.auth.service

import com.auth.dto.MemberDtoRequest
import com.auth.entity.Member
import com.auth.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        // email 중복 확인
        var member: Member? = memberRepository.findByEmail(memberDtoRequest.email)
        if (member != null) {
            return "이미 등록된 email 입니다."
        }

        member = Member(
            email = memberDtoRequest.email,
            nickName = memberDtoRequest.nickName
        )

        memberRepository.save(member)

        return "회원가입이 완료되었습니다."
    }
}