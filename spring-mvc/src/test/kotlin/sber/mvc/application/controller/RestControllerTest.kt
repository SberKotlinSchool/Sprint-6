package sber.mvc.application.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import sber.mvc.application.model.AddressBookEntry
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RestControllerTest {

    @LocalServerPort
    private val port = 8081

    @Autowired
    private lateinit var rest: TestRestTemplate

    private fun url(urls: String) = "http://localhost:$port/$urls"

    private fun headers() = HttpHeaders().apply {
        add("Cookie", "auth=${LocalDateTime.now()}")
    }

    private val client = AddressBookEntry(
        firstName = "Irina",
        lastName = "Romanova",
        address = "Moscow",
        phone = "8-999-999-99-99",
        email = "test@mail.ru"
    )

    @Test
    fun addEntry() {
        val response = rest.postForEntity(
            url("api/add"),
            HttpEntity(client, headers()),
            AddressBookEntry::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun listEntries() {
        rest.postForEntity(url("api/add"), HttpEntity(client, headers()), AddressBookEntry::class.java)
        val response = rest.exchange(
            url("api/list"),
            HttpMethod.GET,
            HttpEntity(null, headers()),
            Array<AddressBookEntry>::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
    }

    @Test
    fun viewEntry() {
        rest.postForEntity(url("api/add"), HttpEntity(client, headers()), AddressBookEntry::class.java)
        val response = rest.exchange(
            url("api/1/view"),
            HttpMethod.GET,
            HttpEntity(null, headers()),
            Unit::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun editEntry() {
        rest.postForEntity(url("api/add"), HttpEntity(client, headers()), AddressBookEntry::class.java)
        val response = rest.exchange(
            url("api/1/edit"),
            HttpMethod.PUT,
            HttpEntity(client.copy(firstName = "Polina"), headers()),
            AddressBookEntry::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun deleteEntry() {
        rest.postForEntity(url("api/add"), HttpEntity(client, headers()), AddressBookEntry::class.java)
        val response = rest.exchange(
            url("api/1/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, headers()),
            Unit::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
    }
}