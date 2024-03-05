package com.auth.dto

import com.common.domain.member.Member
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder


data class MemberDtoRequest(

    @field:[NotNull NotBlank]
    val email: String,
    val nickName: String?,
    @field:[NotNull NotBlank]
    @field:Pattern(
        regexp ="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    val password: String
) {
    fun toEntity(): Member {
        val encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
        return Member(
            email = email,
            nickName = nickName,
            password = encoder.encode(password)
        )
    }
}

data class MemberLoginDtoRequest(
    @field:[NotNull NotBlank]
    val email: String,
    @field:[NotNull NotBlank]
    val password: String
)
