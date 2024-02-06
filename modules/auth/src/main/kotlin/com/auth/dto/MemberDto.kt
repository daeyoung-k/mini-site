package com.auth.dto

import com.auth.entity.Member
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


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
        val encoder = BCryptPasswordEncoder()
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
