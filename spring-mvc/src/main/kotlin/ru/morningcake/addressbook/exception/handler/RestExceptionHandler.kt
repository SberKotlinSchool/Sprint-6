package ru.morningcake.addressbook.exception.handler

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.lang.NonNull
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.morningcake.addressbook.exception.EntityNotFoundException
import ru.morningcake.addressbook.exception.ValidationException
import java.util.*

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class RequestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFoundException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val status: HttpStatus = HttpStatus.NOT_FOUND
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), status, request)
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val status: HttpStatus = HttpStatus.BAD_REQUEST
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), status, request)
    }

    @ExceptionHandler(RuntimeException::class, Exception::class)
    fun handleServiceException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), status, request)
    }

    @NonNull
    override fun  handleExceptionInternal(
        ex: java.lang.Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = if (Objects.isNull(body) || "" == body) ex.message else body
        return super.handleExceptionInternal(ex, apiError, headers, status, request)
    }
}