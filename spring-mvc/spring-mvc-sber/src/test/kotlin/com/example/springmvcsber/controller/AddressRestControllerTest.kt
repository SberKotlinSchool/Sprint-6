package com.example.springmvcsber.controller

import com.example.springmvcsber.entity.Address
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AddressRestControllerTest {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @LocalServerPort
    private var port: Int = 0

    private val testAddress = Address(id = 1, name = "Somename", city = "Moscow", phone = "123123")

    @Test
    @Order(1)
    fun requestWithoutCookiesRedirectLoginTest() {
        val response = restTemplate.exchange(
            url("rest/list"), HttpMethod.GET, HttpEntity(null, null), String::class.java
        )
        assertTrue { response.statusCode == HttpStatus.OK }
        assertTrue { response.body.toString().contains("login") }
        assertTrue { response.body.toString().contains("password") }
    }

    @Test
    @Order(2)
    fun requestListReturnsEmptyListTest() {
        val response = requestGetList(authenticationHeader())
        assertTrue { response.statusCode == HttpStatus.OK }
        assertTrue { response.body?.isEmpty()!! }
    }

    @Test
    @Order(3)
    fun requestAddRedirectsNotEmptyListTest() {
        val responseAdd = restTemplate.exchange(
            url("/rest"), HttpMethod.POST,
            HttpEntity(testAddress, authenticationHeader()), Address::class.java
        )
        assertTrue { responseAdd.statusCode == HttpStatus.OK }
        val responseList = requestGetList(authenticationHeader())
        assertTrue { responseList.body!!.isNotEmpty() }
        assertTrue { responseList.body!![0] == testAddress }
    }

    private fun url(endpoint: String) = "http://localhost:$port/$endpoint"

    private fun authenticationHeader() = HttpHeaders().apply { add("Cookie", "authentication=${LocalDateTime.now()}") }

    private fun requestGetList(headers: HttpHeaders) = restTemplate.exchange(
        url("rest/list"),
        HttpMethod.GET,
        HttpEntity(null, headers),
        Array<Address>::class.java
    )
}