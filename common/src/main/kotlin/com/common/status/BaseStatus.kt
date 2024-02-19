package com.common.status

enum class ResultCode(val msg: String) {
    SUCCESS("정상 처리 되었습니다."),
    ERROR("오류가 발생하였습니다."),
}

enum class ErrorCode(val code: Int, val msg: String) {
    TOKEN_EXPIRED(401,  "토큰이 만료되었습니다."),
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),
    UNSUPPORTED_TOKEN(401, "지원되지 않는 토큰입니다."),
}