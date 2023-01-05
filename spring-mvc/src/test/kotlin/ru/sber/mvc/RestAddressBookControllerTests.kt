package ru.sber.mvc

import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import ru.sber.mvc.domain.Record
import java.net.URL
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestAddressBookControllerTests {

    private companion object {
        const val DEFAULT_RECORD_ID = "0"
        val DEFAULT_RECORD_VALUE = Record(
            "Dima",
            "+79876543211",
            "Saint-Petersburg",
            "Gym buddy",
            id = DEFAULT_RECORD_ID
        )
    }

    @LocalServerPort
    private var currentPort: Int = 0

    private fun url(path: String): String =
        URL("http", "localhost", currentPort, path).toString()

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val headers: HttpHeaders
        get() = HttpHeaders().apply { add("cookie", "auth=${LocalDateTime.now()}") }


    init {
        mockkStatic(UUID::class.java.name)
        every { UUID.randomUUID().toString() } returns DEFAULT_RECORD_ID
    }

    @Test
    fun `check valid get list request`() {
        val request = HttpEntity(null, headers)

        val response = restTemplate.exchange(url("/api/list"), HttpMethod.GET, request, Map::class.java)

        response.run {
            assertEquals(HttpStatus.OK, statusCode)
            assertTrue(body!!.contains("records"))
        }
    }

    @Test
    fun `check valid post add request`() {
        val request = HttpEntity(DEFAULT_RECORD_VALUE, headers)

        val response = restTemplate.exchange(url("/api/add"), HttpMethod.POST, request, Map::class.java)

        response.run {
            assertEquals(HttpStatus.OK, statusCode)
            assertTrue(body!!.contains("id"))
        }
    }

    @Test
    fun `check valid get view request`() {
        val request = HttpEntity(DEFAULT_RECORD_VALUE, headers)

        val response = restTemplate.exchange(
            url("/api/${DEFAULT_RECORD_ID}/view"),
            HttpMethod.GET,
            request,
            Map::class.java
        )
        response.run {
            assertEquals(HttpStatus.OK, statusCode)
            assertTrue(body!!.contains("record"))
        }
    }

    @Test
    fun `check valid post edit request`() {
        val request = HttpEntity(DEFAULT_RECORD_VALUE, headers)

        val response = restTemplate.exchange(
            url("/api/${DEFAULT_RECORD_ID}/edit"),
            HttpMethod.POST,
            request,
            Map::class.java
        )

        response.run {
            assertEquals(HttpStatus.OK, statusCode)
            assertTrue(body!!.contains("newRecord"))
        }
    }

    @Test
    fun `check valid post delete request`() {
        val request = HttpEntity(DEFAULT_RECORD_VALUE, headers)

        val response = restTemplate.exchange(
            url("/api/${DEFAULT_RECORD_ID}/delete"),
            HttpMethod.POST,
            request,
            Unit::class.java
        )

        response.run {
            assertEquals(HttpStatus.OK, statusCode)
            assertNull(body)
        }
    }
}
