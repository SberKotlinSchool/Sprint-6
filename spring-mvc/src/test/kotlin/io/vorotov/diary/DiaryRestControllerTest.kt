package io.vorotov.diary

import io.vorotov.diary.models.DiaryRecord
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class DiaryRestControllerTest {

    @LocalServerPort
    private val port = 8080

    @Autowired
    private lateinit var rests: TestRestTemplate

    private fun url(urls: String) = "http://localhost:$port/$urls"

    private fun headers() = HttpHeaders().apply {
        add("Cookie", "auth=${LocalDateTime.now()}")
    }

    @Test
    fun `no authentication test`() {
        val response = rests.exchange(url("api/list"), HttpMethod.GET,
                                                    HttpEntity(null, null),
                                                    Unit::class.java)
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    }

    @Test
    fun `successful add test`() {
        val record =  DiaryRecord(date = LocalDate.parse("1111-11-11"), message = "111111111111")
        val response = rests.postForEntity(url("api/add"), HttpEntity(record, headers()), DiaryRecord::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `list without records test`() {
        val response = rests.exchange(url("api/list"), HttpMethod.GET,
                                                            HttpEntity(null, headers()),
                                                            Array<DiaryRecord>::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
        response.body?.let { Assertions.assertEquals(0, it.size) }

    }

    @Test
    fun `list with records test`() {
        val record = DiaryRecord(date = LocalDate.parse("1111-11-11"), message = "111111111111")
        rests.postForEntity(url("api/add"), HttpEntity(record, headers()), DiaryRecord::class.java)
        val response = rests.exchange(url("api/list"),
                                        HttpMethod.GET,
                                        HttpEntity(null, headers()),
                                        Array<DiaryRecord>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
        response.body?.let { Assertions.assertEquals(1, it.size) }
    }

    @Test
    fun `view record test`() {
        val record = DiaryRecord(date = LocalDate.parse("1111-11-11"), message = "111111111111")
        rests.postForEntity(url("api/add"), HttpEntity(record, headers()), DiaryRecord::class.java)
        val response = rests.exchange(url("api/${record.date}/view"),
                                            HttpMethod.GET,
                                            HttpEntity(null, headers()), Unit::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

    }

    @Test
    fun `edit record test`() {
        val record1 = DiaryRecord(date = LocalDate.parse("1111-11-11"), message = "111111111111")
        val record2 = DiaryRecord(date = LocalDate.parse("2222-12-22"), message = "222222222222")
        rests.postForEntity(url("api/add"), HttpEntity(record1, headers()), DiaryRecord::class.java)
        val response = rests.exchange(url("api/${record1.date}/edit"),
                                        HttpMethod.POST,
                                        HttpEntity(record2, headers()), DiaryRecord::class.java)

        val response2 = rests.exchange(url("api/list"),
                                        HttpMethod.GET,
                                        HttpEntity(null, headers()),
                                        Array<DiaryRecord>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        response2.body?.let { Assertions.assertEquals(record2, it.first()) }
    }

    @Test
    fun `delete record test`() {
        val record =  DiaryRecord(date = LocalDate.parse("1111-11-11"), message = "111111111111")
        rests.postForEntity(url("api/add"), HttpEntity(record, headers()), DiaryRecord::class.java)
        val response = rests.exchange(url("api/${record.date}/delete"),
                                        HttpMethod.DELETE,
                                        HttpEntity(null, headers()), Unit::class.java)

        val response2 = rests.exchange(url("api/list"),
                                        HttpMethod.GET,
                                        HttpEntity(null, headers()),
                                        Array<DiaryRecord>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        response2.body?.let { Assertions.assertEquals(0, it.size) }
    }


}