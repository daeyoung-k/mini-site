package com.auth.status.jwt

enum class JwtErrorCode(val code: Int, val msg: String) {
    TOKEN_EXPIRED(401,  "토큰이 만료되었습니다."),
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),
    UNSUPPORTED_TOKEN(401, "지원되지 않는 토큰입니다."),
    USER_NOT_FOUND(401, "회원정보가 없습니다."),
}