package com.example.adressbook.controller

import com.example.adressbook.dto.ResponseDTO
import com.example.adressbook.exception.BusinessException
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice(annotations = [RestController::class])
class AddressControllerAdvice {

    @ExceptionHandler(BusinessException::class)
    @ResponseBody
    fun handleBusinessException(e: BusinessException): ResponseEntity<ResponseDTO<Any>> {
        logger.error(e.errorMessage, e)
        return ResponseEntity(ResponseDTO(success = false, error = e.errorMessage), e.responseCode)
    }

    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun handleCommonException(e: Throwable): ResponseEntity<ResponseDTO<Any>> {
        logger.error("Произошла внутренняя ошибка сервиса", e)
        return ResponseEntity(
            ResponseDTO(success = false, error = "Произошла внутренняя ошибка сервиса"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    companion object: KLogging()
}