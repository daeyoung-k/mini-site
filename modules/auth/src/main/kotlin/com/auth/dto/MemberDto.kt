package com.auth.dto

import com.auth.domain.Member
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder


data class MemberDtoRequest(

    @field:[NotNull NotBlank]
    val email: String,
    val name: String,
    @field:[NotNull NotBlank]
    @field:Pattern(
        regexp ="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    val password: String
) {
    fun toEntity(): Member {
        return Member(
            email = email,
            name = name
        )
    }
}

data class MemberLoginDtoRequest(
    @field:[NotNull NotBlank]
    val email: String,
    @field:[NotNull NotBlank]
    val password: String
)
