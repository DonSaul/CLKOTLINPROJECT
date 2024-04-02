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
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse<String>> {
        return mapResponseEntity(ex, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<ErrorResponse<String>>{
        return mapResponseEntity(ex, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse<String>>{
        return mapResponseEntity(ex, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalAccessException::class)
    fun handleIllegalAccessException(ex: IllegalAccessException): ResponseEntity<ErrorResponse<String>>{
        return mapResponseEntity(ex, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(ex: ForbiddenException): ResponseEntity<ErrorResponse<String>>{
        return mapResponseEntity(ex, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllException(ex: Exception): ResponseEntity<ErrorResponse<String>>{
        return mapResponseEntity(ex)
    }

    /**
     * Maps the exception to a standar response.
     *
     * @param ex The body of the response.
     * @param status The HTTP status of the response. Default is HttpStatus.INTERNAL_SERVER_ERROR.
     * @return The ResponseEntity containing the mapped exception.
     */
    private fun mapResponseEntity(
        ex: Exception,
        status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR): ResponseEntity<ErrorResponse<String>>{

        val bodyContent = ErrorResponse(
            status = status.value(),
            data = ex.message,
            message = ex.message!!
        )
        return ResponseEntity
            .status(status)
            .body(bodyContent)
    }
}