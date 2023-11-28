package ru.sber.api

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sber.AuthCookie
import ru.sber.api.dto.ListRecordResponse
import ru.sber.api.dto.RecordRequest
import ru.sber.api.dto.RecordResponse
import ru.sber.model.record.RecordService
import javax.servlet.http.Cookie

@RestController
@RequestMapping("/api")
class RestfullController(val service: RecordService) {

  @GetMapping(path = ["/list"], produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getList(@CookieValue("auth") cookie: Cookie): ResponseEntity<ListRecordResponse> {
    val authCookie = AuthCookie.fromString(cookie.value)
    return ResponseEntity.ok(ListRecordResponse(service.getAllRecord(authCookie.userId)))
  }

  @GetMapping(path = ["/search"], produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getSearchList(@RequestParam query: String, @CookieValue("auth") cookie: Cookie):
    ResponseEntity<ListRecordResponse> {
    val authCookie = AuthCookie.fromString(cookie.value)
    return ResponseEntity.ok(ListRecordResponse(service.search(query, authCookie.userId)))
  }

  @PostMapping(path = ["/add"],
    consumes = [MediaType.APPLICATION_JSON_VALUE])
  fun addRecord(@RequestBody requestRecord: RecordRequest, @CookieValue("auth") cookie: Cookie): ResponseEntity<Any> {
    val authCookie = AuthCookie.fromString(cookie.value)
    service.createRecord(requestRecord.toRecordDto(), authCookie.userId)
    return ResponseEntity.ok().build()
  }

  @GetMapping(path = ["{id}/view"], produces = [MediaType.APPLICATION_JSON_VALUE])
  fun viewRecord(@CookieValue("auth") cookie: Cookie, @PathVariable id: Int): ResponseEntity<RecordResponse> {
    val authCookie = AuthCookie.fromString(cookie.value)
    return service.getRecordById(id, authCookie.userId)?.let {
      ResponseEntity.ok(RecordResponse.fromRecordDTO(it))
    } ?: ResponseEntity.notFound().build()
  }

  @PostMapping(path = ["/{id}/edit"],
    consumes = [MediaType.APPLICATION_JSON_VALUE])
  fun editRecord(@RequestBody requestRecord: RecordRequest, @CookieValue("auth") cookie: Cookie, @PathVariable id: Int): ResponseEntity<Any> {
    val authCookie = AuthCookie.fromString(cookie.value)
    service.modifyRecord(requestRecord.toRecordDto(id), authCookie.userId)
    return ResponseEntity.ok().build()
  }

  @PostMapping(path = ["/{id}/delete"])
  fun deleteRecord(@CookieValue("auth") cookie: Cookie, @PathVariable id: Int): ResponseEntity<Any> {
    val authCookie = AuthCookie.fromString(cookie.value)
    service.deleteRecordById(id, authCookie.userId)
    return ResponseEntity.ok().build()
  }
}