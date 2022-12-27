package ru.sber.mvc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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
import ru.sber.mvc.models.AddressRow
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RestApiControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(path: String): String {
        return "http://localhost:${port}/${path}"
    }

    fun getHeaders(): HttpHeaders {
        var headers = HttpHeaders()
        headers.add("Cookie", "auth=${LocalDateTime.now()}")
        return headers
    }

    @Test
    fun `test get list api`() {

        var responce = restTemplate.exchange(
            url("/api/list"),
            HttpMethod.GET,
            HttpEntity(null, getHeaders()),
            Array<AddressRow>::class.java
        )

        assertNotNull(responce)
        assertNotNull(responce.body)
        assertEquals(HttpStatus.OK, responce.statusCode)

        assertEquals(3, responce.body?.size)
        assertEquals(responce.body!!.first().name, "John")
        assertEquals(responce.body!!.first().phone, "7-924-202-23-01")
    }

    @Test
    fun `test get list api with parameter`() {

        var responce = restTemplate.exchange(
            url("/api/list?name=Billy&phone=7-924-202-35-02"),
            HttpMethod.GET,
            HttpEntity(null, getHeaders()),
            Array<AddressRow>::class.java
        )

        assertNotNull(responce)
        assertNotNull(responce.body)
        assertEquals(HttpStatus.OK, responce.statusCode)

        assertEquals(1, responce.body?.size)
        assertEquals(responce.body!!.first().name, "Billy")
        assertEquals(responce.body!!.first().phone, "7-924-202-35-02")
    }

    @Test
    fun `test get view api`() {

        var responce = restTemplate.exchange(
            url("/api/0/view"),
            HttpMethod.GET,
            HttpEntity(null, getHeaders()),
            AddressRow::class.java
        )

        assertNotNull(responce)
        assertNotNull(responce.body)
        assertEquals(HttpStatus.OK, responce.statusCode)

        assertEquals(responce.body!!.name, "John")
        assertEquals(responce.body!!.phone, "7-924-202-23-01")
    }

    @Test
    fun `test add api`() {

        var responce = restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            HttpEntity(AddressRow(name = "Jimmy", phone = "7-956-489-51-62"), getHeaders()),
            AddressRow::class.java
        )

        assertNotNull(responce)
        assertNotNull(responce.body)
        assertEquals(HttpStatus.OK, responce.statusCode)
    }

    @Test
    fun `test edit api`() {

        var responce = restTemplate.exchange(
            url("api/0/edit"),
            HttpMethod.PUT,
            HttpEntity(AddressRow(id = 0, name = "Alexander", phone = "7-956-489-51-62", descr = "Bad man"), getHeaders()),
            AddressRow::class.java
        )

        assertNotNull(responce)
        assertNotNull(responce.body)
        assertEquals(HttpStatus.OK, responce.statusCode)
        assertEquals(responce.body!!.name, "Alexander")
        assertEquals(responce.body!!.phone, "7-956-489-51-62")
    }

    @Test
    fun `test delete api`() {

        var responce = restTemplate.exchange(
            url("/api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, getHeaders()),
            AddressRow::class.java
        )

        assertEquals(HttpStatus.OK, responce.statusCode)
    }
}