package ru.sber.mvc

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
import org.springframework.test.annotation.DirtiesContext
import org.springframework.util.LinkedMultiValueMap
import ru.sber.mvc.domain.DomainRecord
import java.net.URL

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RestAddressBookControllerTests {

    private companion object {
        const val DEFAULT_RECORD_ID = 1L
        val DEFAULT_RECORD_VALUE = DomainRecord(
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

    @Test
    fun `check valid get list request`() {
        val request = HttpEntity(null, getAuthHeaders("user2", "user2", "/login"))

        val response = restTemplate.exchange(url("/api/list"), HttpMethod.GET, request, Map::class.java)

        response.run {
            assertEquals(HttpStatus.OK, statusCode)
            assertTrue(body!!.contains("records"))
        }
    }

    @Test
    fun `check valid post add request`() {
        val request = HttpEntity(DEFAULT_RECORD_VALUE, getAuthHeaders("user2", "user2", "/login"))

        val response = restTemplate.exchange(url("/api/add"), HttpMethod.POST, request, Map::class.java)

        response.run {
            assertEquals(HttpStatus.OK, statusCode)
            assertTrue(body!!.contains("id"))
        }
    }

    @Test
    fun `check valid get view request`() {
        val request = HttpEntity(DEFAULT_RECORD_VALUE, getAuthHeaders("user2", "user2", "/login"))

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
    fun `check invalid post delete request`() {
        val request = HttpEntity(DEFAULT_RECORD_VALUE, getAuthHeaders("user2", "user2", "/login"))

        val response = restTemplate.exchange(
            url("/api/${DEFAULT_RECORD_ID}/delete"),
            HttpMethod.POST,
            request,
            Unit::class.java
        )

        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
    }

    @Test
    fun `check valid post delete request`() {
        val request = HttpEntity(DEFAULT_RECORD_VALUE, getAuthHeaders("admin", "admin", "/login"))

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

    private fun getAuthHeaders(username: String, password: String, url: String): HttpHeaders {
        // credentials for api user
        val credentials = LinkedMultiValueMap<String, String>()
            .apply {
                set("username", username)
                set("password", password)
            }
        val request = HttpEntity(credentials, HttpHeaders())

        val response = restTemplate.postForEntity(url, request, String::class.java)
        return HttpHeaders().apply { set("Cookie", response.headers["Set-Cookie"].orEmpty().first()) }
    }
}
