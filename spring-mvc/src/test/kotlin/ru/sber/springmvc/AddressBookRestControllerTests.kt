package ru.sber.springmvc


import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import java.time.LocalDateTime
import org.springframework.http.*
import ru.sber.springmvc.domain.Record
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressBookRestControllerTests {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val host: String = "localhost"

    @LocalServerPort
    private val port: Int = 0
    private val record = Record(3, "Pushkin's library", "Kamennoostrovskiy, 35")


    private fun getUrl(endPoint: String, isSecured: Boolean): String {
        return if (isSecured) {
            "https://$host:$port/$endPoint"
        } else "http://$host:$port/$endPoint"
    }

    @Test
    fun shouldCheckAdd() {
        val response = restTemplate.exchange(
            getUrl("/api/add", false),
            HttpMethod.POST,
            HttpEntity(record, setCookie()),
            String::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun shouldCheckGetList() {
        val response = restTemplate.exchange(
            getUrl("/api/list", false),
            HttpMethod.GET,
            HttpEntity(record, setCookie()),
            String::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(
            "[{\"id\":1,\"name\":\"Red Dragon\",\"address\":\"Lenina str, 2\"},{\"id\":2,\"name\":\"Lenin's Library\",\"address\":\"Voskova str, 2\"}]",
            response.body
        )
    }

    @Test
    fun shouldCheckViewById() {
        val response = restTemplate.exchange(
            getUrl("/api/1/view", false),
            HttpMethod.GET,
            HttpEntity(record, setCookie()),
            String::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("{\"id\":1,\"name\":\"Red Dragon\",\"address\":\"Lenina str, 2\"}", response.body)
    }


    @Test
    fun shouldCheckDelete() {
        val response = restTemplate.exchange(
            getUrl("/api/2/delete", false),
            HttpMethod.DELETE,
            HttpEntity(record, setCookie()),
            String::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
    }


    fun setCookie(): HttpHeaders {
        val httpHeader = HttpHeaders()
        httpHeader.add("Cookie", "auth=${LocalDateTime.now()}")
        return httpHeader
    }
}
