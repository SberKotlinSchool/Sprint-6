package ru.sber.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.*
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
import ru.sber.dto.Address
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class RestControllerTest {

    @LocalServerPort
    private var port = 0
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val address = Address("n", "a", "p")

    private fun String.url() = "http://localhost:$port/$this"
    private fun cookieHeader() = HttpHeaders().apply { add("Cookie", "auth=${LocalDateTime.now()}") }
    private fun addAddress() =
        restTemplate.postForEntity<Address>("api/add".url(), HttpEntity(address, cookieHeader()))
    private fun firstAddress() = restTemplate.getForEntity<Address>("api/0/view".url(), cookieHeader())

    @Test
    fun add() {
        val res = addAddress()//exchange<Address>("api/add".url(), HttpMethod.POST, HttpEntity(address, cookieHeader()))

        assertEquals(HttpStatus.OK, res.statusCode)
    }

    @Test
    fun list() {
        addAddress()
        val res = restTemplate.getForEntity<Map<Long, Address>>("api/list".url(), cookieHeader())

        assertEquals(HttpStatus.OK, res.statusCode)
        assertEquals(1, res.body?.values?.size)
    }

    @Test
    fun view() {
        addAddress()
        val res = firstAddress()

        assertEquals(HttpStatus.OK, res.statusCode)
        assertEquals(address, res.body)
    }

    @Test
    fun edit() {
        addAddress()
        val newAddress = Address("N", "A", "P")
        val res = restTemplate.exchange<Address>("api/0/edit".url(), HttpMethod.PUT, HttpEntity(newAddress, cookieHeader()))
        val updatedAddress = firstAddress().body

        assertEquals(HttpStatus.OK, res.statusCode)
        assertEquals(newAddress, updatedAddress)
    }

    @Test
    fun delete() {
        addAddress()
        val res = restTemplate.exchange<Address>("api/0/delete".url(), HttpMethod.DELETE, HttpEntity<Address>(cookieHeader()))

        assertEquals(HttpStatus.OK, res.statusCode)
        assertEquals(address, res.body)
    }
}