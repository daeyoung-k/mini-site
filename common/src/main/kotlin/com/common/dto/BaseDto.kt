package com.common.dto

import com.common.status.ResultCode

data class BaseResponse<T>(
    val resultCode: String = ResultCode.SUCCESS.name,
    val message: String = ResultCode.SUCCESS.msg,
    val data: T? = null
)


data class ErrorResponse(val code: Int, val message: String)