package ru.sber.addresses.controllers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.annotation.DirtiesContext
import ru.sber.addresses.dto.Address
import ru.sber.addresses.requests.GetAddressRq

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ApiControllerTest {
    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(path: String): String {
        return "http://localhost:${port}/${path}"
    }
    @Test
    fun getAddresses() {
        val actualResponse = restTemplate.exchange(
            url("view"),
            HttpMethod.GET,
            HttpEntity("{id : 1}", HttpHeaders()),
            Array<Address>::class.java
        )
        assertNotNull(actualResponse)
    }
}