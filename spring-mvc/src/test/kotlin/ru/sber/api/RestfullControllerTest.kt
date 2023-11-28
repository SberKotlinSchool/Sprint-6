package ru.sber.api

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import ru.sber.AuthCookie
import ru.sber.api.dto.ListRecordResponse
import ru.sber.api.dto.RecordRequest
import ru.sber.api.dto.RecordResponse
import ru.sber.model.record.RecordDAO
import ru.sber.model.record.RecordDTO
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.Cookie


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestfullControllerTest {

  @LocalServerPort
  private var port: Int = 80

  @Autowired
  private lateinit var restTemplate: TestRestTemplate

  @Autowired
  private lateinit var repository: RecordDAO

  companion object {
    val RECORD = RecordDTO(1, "user1", "name", "lastName", "secondName", "phoneNumber",
      RecordDTO.Address("city", "street", 1, 123456))
  }

  private fun url(path: String): String {
    return "http://localhost:${port}/${path}"
  }

  @BeforeEach
  fun setUp() {
    repository.clear()
    restTemplate = TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES,
      TestRestTemplate.HttpClientOption.ENABLE_REDIRECTS)
  }

  @AfterEach
  fun tearDown() {
  }

  fun initRepository(name: String) {
    repository.setUp(
      ConcurrentHashMap(mapOf(
        1 to RECORD.copy(name = name))))
  }

  @Test
  fun getList() {
    val name = "testApiList"
    initRepository(name)
    val headers = HttpHeaders()
    headers.add("Cookie", "auth=${AuthCookie("user1", LocalDateTime.now())}")

    val resp = restTemplate.exchange(
      url("api/list"),
      HttpMethod.GET,
      HttpEntity<Cookie>(headers),
      ListRecordResponse::class.java)

    assertNotNull(resp.body)
    assertEquals(1, resp.body!!.list.size)
    assertEquals(RECORD.copy(name = name), resp.body!!.list.first())
  }

  @Test
  fun getSearchList() {
    val name = "testApiSearchList"
    initRepository(name)
    val headers = HttpHeaders()
    headers.add("Cookie", "auth=${AuthCookie("user1", LocalDateTime.now())}")

    val resp = restTemplate.exchange(
      url("api/list"),
      HttpMethod.GET,
      HttpEntity<Cookie>(headers),
      ListRecordResponse::class.java)

    assertNotNull(resp.body)
    assertEquals(1, resp.body!!.list.size)
    assertEquals(RECORD.copy(name = name), resp.body!!.list.first())
  }

  @Test
  fun addRecord() {
    val name = "testApiAdd"
    val record = RECORD.copy(name = name)
    val headers = HttpHeaders()
    headers.add("Cookie", "auth=${AuthCookie("user1", LocalDateTime.now())}")

    val respAdd = restTemplate.exchange(
      url("api/add"),
      HttpMethod.POST,
      HttpEntity(RecordRequest(record), headers),
      Any::class.java)

    assertEquals(HttpStatus.OK, respAdd.statusCode)

    val respList = restTemplate.exchange(
      url("api/list"),
      HttpMethod.GET,
      HttpEntity<Cookie>(headers),
      ListRecordResponse::class.java)

    assertNotNull(respList.body)
    assertEquals(1, respList.body!!.list.size)
    assertEquals(RECORD.copy(name = name), respList.body!!.list.first())
  }

  @Test
  fun viewRecord() {
    val name = "testApiView"
    initRepository(name)
    val headers = HttpHeaders()
    headers.add("Cookie", "auth=${AuthCookie("user1", LocalDateTime.now())}")

    val resp = restTemplate.exchange(
      url("api/1/view"),
      HttpMethod.GET,
      HttpEntity<Cookie>(headers),
      RecordResponse::class.java)

    assertNotNull(resp.body)
    assertEquals(RecordResponse.fromRecordDTO(RECORD.copy(name = name)), resp.body!!)
  }

  @Test
  fun editRecord() {
    val name = "testApi"
    initRepository(name)
    val newName = "testApiEdit"
    val record = RECORD.copy(name = newName)
    val headers = HttpHeaders()
    headers.add("Cookie", "auth=${AuthCookie("user1", LocalDateTime.now())}")

    val respEdit = restTemplate.exchange(
      url("api/1/edit"),
      HttpMethod.POST,
      HttpEntity(RecordRequest(record), headers),
      Any::class.java)

    assertEquals(HttpStatus.OK, respEdit.statusCode)

    val respList = restTemplate.exchange(
      url("api/list"),
      HttpMethod.GET,
      HttpEntity<Cookie>(headers),
      ListRecordResponse::class.java)

    assertNotNull(respList.body)
    assertEquals(1, respList.body!!.list.size)
    assertEquals(record, respList.body!!.list.first())
  }

  @Test
  fun deleteRecord() {
    val name = "testApiDelete"
    initRepository(name)
    val headers = HttpHeaders()
    headers.add("Cookie", "auth=${AuthCookie("user1", LocalDateTime.now())}")

    val respEdit = restTemplate.exchange(
      url("api/1/delete"),
      HttpMethod.POST,
      HttpEntity<Cookie>(headers),
      Any::class.java)

    assertEquals(HttpStatus.OK, respEdit.statusCode)

    val respList = restTemplate.exchange(
      url("api/list"),
      HttpMethod.GET,
      HttpEntity<Cookie>(headers),
      ListRecordResponse::class.java)

    assertNotNull(respList.body)
    assertEquals(0, respList.body!!.list.size)
  }
}