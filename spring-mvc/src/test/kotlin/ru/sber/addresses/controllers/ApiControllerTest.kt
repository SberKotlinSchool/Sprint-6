package ru.sber.addresses.controllers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext
import ru.sber.addresses.dto.Address
import ru.sber.addresses.requests.CreateAddressRq
import ru.sber.addresses.requests.UpdateAddressRq

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ApiControllerTest {
    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val fullName = "Иванов Иван Иванович"
    private val postAddress = "г. Москва, Ленинский проспект, 32, кв. 18"
    private val phoneNumber = "+78000000000"
    private val email = "test@test.ru"

    var counter = 0

    private fun url(path: String): String {
        return "http://localhost:${port}/api/${path}"
    }

    @Test
    fun getAddress() {
        val id = addAddress().body.also { counter++ }
        val actualResponse = restTemplate.exchange(
            url("$id/view"),
            HttpMethod.GET,
            HttpEntity(null, HttpHeaders()),
            Array<Address>::class.java
        )
        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(1, actualResponse.body?.size)
        assertAddress(actualResponse.body!!.first())
    }

    @Test
    fun getAddresses() {
        addAddress().body.also { counter++ }
        val actualResponse = restTemplate.exchange(
            url("view"),
            HttpMethod.GET,
            HttpEntity(null, HttpHeaders()),
            Array<Address>::class.java
        )
        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(counter, actualResponse.body?.size)
        assertAddress(actualResponse.body!![counter - 1])
    }

    @Test
    fun updateAddressWhenOkResult() {
        val id = addAddress().body.also { counter++ }
        val address = UpdateAddressRq(id!!, CreateAddressRq("${fullName}_edited", postAddress, phoneNumber, email))
        val actualResponse = restTemplate.exchange(
            url("$id/edit"),
            HttpMethod.PUT,
            HttpEntity(address, HttpHeaders()),
            Address::class.java
        )
        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals("${fullName}_edited", actualResponse.body!!.fullName)
    }

    @Test
    fun updateAddressWhenIsNotOkResult() {
        val id = addAddress().body.also { counter++ }
        val address = UpdateAddressRq(id!! + 1, CreateAddressRq("${fullName}_edited", postAddress, phoneNumber, email))
        val actualResponse = restTemplate.exchange(
            url("${id + 1}/edit"),
            HttpMethod.PUT,
            HttpEntity(address, HttpHeaders()),
            Address::class.java
        )
        assertNotNull(actualResponse)
        assertNull(actualResponse.body)
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.statusCode)
    }

    @Test
    fun deleteAddressWhenOkResult() {
        val id = addAddress().body.also { counter++ }
        val actualResponse = restTemplate.exchange(
            url("$id/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, HttpHeaders()),
            Unit::class.java
        )
        assertNotNull(actualResponse)
        assertNull(actualResponse.body)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
    }

    @Test
    fun deleteAddressWhenIsNotOkResult() {
        val id = addAddress().body.also { counter++ }
        val actualResponse = restTemplate.exchange(
            url("${id!! + 1}/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, HttpHeaders()),
            Unit::class.java
        )
        assertNotNull(actualResponse)
        assertNull(actualResponse.body)
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.statusCode)
    }

    private fun addAddress(): ResponseEntity<Long> {
        val address = CreateAddressRq(fullName, postAddress, phoneNumber, email)
        return restTemplate.postForEntity(
            url(""),
            HttpEntity(address, HttpHeaders()),
            Long::class.java
        )
    }

    private fun assertAddress(address: Address) {
        assertEquals(fullName, address.fullName)
        assertEquals(postAddress, address.postAddress)
        assertEquals(phoneNumber, address.phoneNumber)
        assertEquals(email, address.email)
    }
}