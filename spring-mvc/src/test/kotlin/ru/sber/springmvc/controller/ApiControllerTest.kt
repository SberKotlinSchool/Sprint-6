package ru.sber.springmvc.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import ru.sber.springmvc.service.BookRow
import ru.sber.springmvc.service.BookService

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ApiControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var service: BookService
    private val one = BookRow("test", "test address")
    private val two = BookRow("test2", "test address 2")

    @BeforeEach
    fun setUp() = service.add(one)
    @AfterEach
    fun tearDown() = service.deleteAll()

    private fun url(path: String) = "http://localhost:${port}${path}"

    @Test
    fun add() {
        val resp = restTemplate.exchange(url("/api/add"), HttpMethod.POST,
            HttpEntity(two, authHeader()), typeReference<List<BookRow>>())

        assertThat(resp.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(resp.headers.containsKey("Location")).isTrue
        assertThat(resp.headers["Location"]).isEqualTo(listOf(url("/api/${two.name}/view")))
    }

    @Test
    fun view() {
        val resp = restTemplate.exchange(url("/api/${one.name}/view"), HttpMethod.GET,
                    HttpEntity<Nothing>(authHeader()), BookRow::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(resp.body?.name).isEqualTo(one.name)
        assertThat(resp.body?.address).isEqualTo(one.address)
    }

    @Test
    fun list() {
        val resp = restTemplate.exchange(url("/api/list"), HttpMethod.GET,
                    HttpEntity<Nothing>(authHeader()), typeReference<List<BookRow>>())

        assertThat(resp.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(resp.body?.size).isEqualTo(1)
        assertThat(resp.body?.get(0)).isEqualTo(one)
    }

    @Test
    fun edit() {
        val resp = restTemplate.exchange(url("/api/${one.name}/edit"), HttpMethod.PUT,
                    HttpEntity(two.address, authHeader()), Nothing::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.ACCEPTED)
    }

    @Test
    fun delete() {
        val resp = restTemplate.exchange(url("/api/${one.name}/delete"), HttpMethod.DELETE,
                    HttpEntity(two.address, authHeader()), Nothing::class.java)

        assertThat(resp.statusCode).isEqualTo(HttpStatus.ACCEPTED)
    }

    private fun authHeader(): HttpHeaders {
        val headers = HttpHeaders()
        headers.setBasicAuth("test", "test")
        return headers
    }
}

inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}