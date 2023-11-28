package ru.sber.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import ru.sber.model.Note
import ru.sber.repository.AddressBookRepository
import java.time.LocalDateTime
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ApiControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var repository: AddressBookRepository

    private fun url(s: String) = "http://localhost:${port}/${s}"

    private val headers = HttpHeaders().also { it.add("Cookie", "auth=${LocalDateTime.now()}") }
    private val testNote = Note("NAME", "ADDRESS", "+89999999999")
    private val testNote2 = Note("NAME2", "ADDRESS2", "+82222222222")

    @BeforeEach
    fun cleanBase() {
        repository.deleteAll()
    }

    @Test
    fun add() {
        //given
        //when
        val resp = restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            HttpEntity(testNote, headers),
            Unit::class.java
        )
        //then
        assertEquals(resp.statusCode, HttpStatus.OK)
        assertEquals(testNote, repository.getById(0L))
    }

    @Test
    fun list() {
        //given
        repository.create(testNote)
        repository.create(testNote2)
        //when
        val resp = restTemplate.exchange(
            url("/api/list"),
            HttpMethod.GET,
            HttpEntity(null, headers),
            Array<Note>::class.java
        )
        //then
        assertEquals(resp.statusCode, HttpStatus.OK)
        assertContentEquals(resp.body?.toList(), listOf(testNote, testNote2))
    }

    @Test
    fun view() {
        //given
        repository.create(testNote)
        repository.create(testNote2)
        //when
        val resp = restTemplate.exchange(
            url("/api/1/view"),
            HttpMethod.GET,
            HttpEntity(null, headers),
            Note::class.java
        )
        //then
        assertEquals(resp.statusCode, HttpStatus.OK)
        assertEquals(resp.body, testNote2)
    }


    @Test
    fun edit() {
        //given
        repository.create(testNote)
        //when
        val resp = restTemplate.exchange(
            url("/api/0/edit"),
            HttpMethod.PUT,
            HttpEntity(testNote2, headers),
            Unit::class.java
        )
        //then
        assertEquals(resp.statusCode, HttpStatus.OK)
        assertEquals(repository.getById(0L), testNote2)
    }

    @Test
    fun delete() {
        //given
        repository.create(testNote)
        //when
        val resp = restTemplate.exchange(
            url("/api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, headers),
            Unit::class.java
        )
        //then
        assertEquals(resp.statusCode, HttpStatus.OK)
        assertNull(repository.getById(0L))
    }

    @ParameterizedTest
    @ValueSource(strings = ["/api/add", "/api/list", "/api/0/view", "/api/0/edit", "/api/0/delete"])
    fun `should login`(url: String) {
        //given
        repository.create(testNote)
        //when
        val resp = restTemplate.exchange(
            url(url),
            HttpMethod.GET,
            HttpEntity(null, null),
            String::class.java
        )
        //then
        assertEquals(resp.statusCode, HttpStatus.OK)
        assertEquals(
            resp.body.toString().trimIndent(), """
                <!DOCTYPE html>
                <html lang="en">
                <body>
                <form action="/login" method="POST">
                    <p>login:<label><input name="login" type="text"></label></p>
                    <p>password:<label><input name="password" type="text"></label></p>
                    <input type="submit" value="login"/>
                </form>
                </body>
                </html>
            """.trimIndent()
        )
    }
}