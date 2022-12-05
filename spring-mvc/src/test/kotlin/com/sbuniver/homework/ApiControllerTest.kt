package com.sbuniver.homework

import com.fasterxml.jackson.databind.ObjectMapper
import com.sbuniver.homework.dto.AddressBook
import com.sbuniver.homework.dto.AddressDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.time.LocalTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class ApiControllerTest {

    @LocalServerPort
    private val port: Int = 0

    lateinit var apiUrl: String

    val restTemplate = RestTemplate()
    val addressBook = AddressBook()

    @BeforeEach
    fun prepare(){
        apiUrl = "http://localhost:$port/api"
    }

    @Test
    fun list() {
        val responseBody = restTemplate
            .getForEntity(URI("$apiUrl/list"), String::class.java).body
        assert(responseBody!!.contains("Andrey"))
    }

    @Test
    fun add() {
        val dto = AddressDto(999,"Newman","NY","NewStreet",9)
        val entity = HttpEntity(dto)
        val responseEntity = restTemplate.exchange(
            URI("$apiUrl/add"),
            HttpMethod.POST,
            entity,
            Boolean::class.java
        )
        assert(responseEntity.statusCode.is2xxSuccessful)
    }

    @Test
    fun getById() {
      val dto = restTemplate.exchange(
            URI("$apiUrl/1/view"),
            HttpMethod.GET,
            HttpEntity.EMPTY,
            AddressDto::class.java
        ).body
        assert(dto!! == addressBook.get(1))
    }

    @Test
    fun editById() {
        val dto = AddressDto(2,"Newman","NY","NewStreet",9)
        val entity = HttpEntity(dto)
        val responseEntity = restTemplate.exchange(
            URI("$apiUrl/edit"),
            HttpMethod.PUT,
            entity,
            AddressDto::class.java
        )
        assert(responseEntity.statusCode.is2xxSuccessful)
    }

    @Test
    fun deleteById() {
        val responseEntity = restTemplate.exchange(
            URI("$apiUrl/1/delete"),
            HttpMethod.DELETE,
            HttpEntity.EMPTY,
            Any::class.java
        )
        assert(responseEntity.statusCode.is2xxSuccessful)
    }
}