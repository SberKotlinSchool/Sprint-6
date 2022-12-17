package ru.sbrf.school.kotlin.springmvc.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception

@RestControllerAdvice
class ErrorHandler {
    @ExceptionHandler
    fun handleException(exception: Exception) : ResponseEntity<ErrorMessageModel>{
        val errorMessageModel = ErrorMessageModel(
            status = HttpStatus.BAD_REQUEST.value(),
            message = exception.message
        )
        return ResponseEntity(errorMessageModel, HttpStatus.BAD_REQUEST)
    }
}



class ErrorMessageModel(
    var status: Int? = null,
    var message: String? = null
)