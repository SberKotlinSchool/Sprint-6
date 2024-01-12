package ru.sber.addressbook.controller

import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import ru.sber.addressbook.dto.ResponseDTO
import ru.sber.addressbook.exception.AddressException

@ControllerAdvice(annotations = [RestController::class])
class AddressControllerAdvice {

  @ExceptionHandler(AddressException::class)
  @ResponseBody
  fun handleBusinessException(e: AddressException): ResponseEntity<ResponseDTO<Any>> {
    logger.error(e.error, e)
    return ResponseEntity(ResponseDTO(success = false, error = e.error), e.responseCode)
  }

  @ExceptionHandler(Throwable::class)
  @ResponseBody
  fun handleCommonException(e: Throwable): ResponseEntity<ResponseDTO<Any>> {
    logger.error("Внутренняя ошибка сервиса", e)
    return ResponseEntity(
        ResponseDTO(success = false, error = "Внутренняя ошибка сервиса"),
        HttpStatus.INTERNAL_SERVER_ERROR
    )
  }

  companion object: KLogging()
}