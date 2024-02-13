package com.auth.exception

import com.common.dto.BaseResponse
import com.common.exception.InvalidInputException
import com.common.status.ResultCode
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.ServletException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage ?: "Not Exception Message"
        }
        return ResponseEntity(
            BaseResponse(
            ResultCode.ERROR.name,
            ResultCode.ERROR.msg,
            errors,
        ), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidInputException::class)
    protected fun invalidInputException(ex: InvalidInputException): ResponseEntity<BaseResponse<String>> {
        return ResponseEntity(
            BaseResponse(
                ResultCode.ERROR.name,
                ex.message ?: ResultCode.ERROR.msg,
            ), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(ex: Exception): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mapOf("미처리 에러" to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(
            BaseResponse(
                ResultCode.ERROR.name,
                ResultCode.ERROR.msg,
                errors
            ), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(BadCredentialsException::class)
    protected fun badCredentialsException(ex: BadCredentialsException): ResponseEntity<BaseResponse<String>> {
        return ResponseEntity(
            BaseResponse(
                ResultCode.ERROR.name,
                ex.message ?: ResultCode.ERROR.msg,
            ), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(SignatureException::class)
    protected fun signatureException(ex: SignatureException): ResponseEntity<BaseResponse<String>> {
        return ResponseEntity(
            BaseResponse(
                ResultCode.ERROR.name,
                ex.message ?: ResultCode.ERROR.msg,
            ), HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ServletException::class)
    protected fun servletException(ex: ServletException): ResponseEntity<BaseResponse<String>> {
        return ResponseEntity(
            BaseResponse(
                ResultCode.ERROR.name,
                ex.message ?: ResultCode.ERROR.msg,
            ), HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(SecurityException::class, MalformedJwtException::class, ExpiredJwtException::class, UnsupportedJwtException::class, IllegalArgumentException::class)
    protected fun tokenValidateException(ex: Exception): ResponseEntity<BaseResponse<String>> {
        return ResponseEntity(
            BaseResponse(
                ResultCode.ERROR.name,
                ex.message ?: ResultCode.ERROR.msg,
            ), HttpStatus.UNAUTHORIZED)
    }

}