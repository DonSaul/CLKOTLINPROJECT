package com.jobsearch.exception

import com.jobsearch.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<ErrorResponse>{
        val body = ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            message = ex.message!!,
            timeStamp = System.currentTimeMillis()
        )

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(body)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse>{
        val body = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message!!,
            timeStamp = System.currentTimeMillis()
        )

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(body)
    }

    @ExceptionHandler(IllegalAccessException::class)
    fun handleIllegalAccessException(ex: IllegalAccessException): ResponseEntity<ErrorResponse>{
        val body = ErrorResponse(
            status = HttpStatus.FORBIDDEN.value(),
            message = ex.message!!,
            timeStamp = System.currentTimeMillis()
        )

        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(body)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllException(ex: Exception): ResponseEntity<ErrorResponse>{
        val body = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = ex.message!!,
            timeStamp = System.currentTimeMillis()
        )

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(body)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(ex: ForbiddenException): ResponseEntity<ErrorResponse>{
        val body = ErrorResponse(
            status = HttpStatus.FORBIDDEN.value(),
            message = ex.message!!,
            timeStamp = System.currentTimeMillis()
        )

        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(body)
    }
}