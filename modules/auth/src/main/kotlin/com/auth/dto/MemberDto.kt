package com.auth.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


data class MemberDtoRequest(
    @field:[NotNull NotBlank]
    val email: String,
    @field:[NotNull NotBlank]
    val nickName: String?
)
