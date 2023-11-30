package com.example.demo.controller

import com.example.demo.service.AddressBookModel
import com.example.demo.service.AddressBookService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApiControllerTest {

    @LocalServerPort
    private var port: Int = 0
    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    @Autowired
    private lateinit var service: AddressBookService

    private val addressBookModel = AddressBookModel("John Doe", "901-387-2344", "johndoe@example.com")

    private fun url(path: String): String {
        return "http://localhost:${port}/${path}"
    }

    private fun headers(): HttpHeaders {
        return HttpHeaders().apply {
            put(HttpHeaders.COOKIE, listOf("auth=${ZonedDateTime.now()}"))
        }
    }

    @Test
    fun `verify add`() {
        val response = restTemplate.postForEntity(url("api/add"),
            HttpEntity(addressBookModel, headers()), AddressBookModel::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `verify view`() {
        service.add(addressBookModel)
        val response = restTemplate.exchange(url("api/${addressBookModel.id}/view"),
            HttpMethod.GET, HttpEntity(null, headers()), AddressBookModel::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(addressBookModel)
    }

    @Test
    fun `verify list`() {
        service.add(addressBookModel)

        val response = restTemplate.exchange(
            /* url = */ url("api/list"),
            /* method = */HttpMethod.GET,
            /* requestEntity = */HttpEntity(null, headers()),
            /* responseType = */object : ParameterizedTypeReference<List<AddressBookModel>>() {}
        )

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(listOf(addressBookModel))
    }

    @Test
    fun `verify edit`() {
        service.add(addressBookModel)
        val expected = addressBookModel.copy(name = "Jane Doe")

        val response = restTemplate.postForEntity(
            url("api/${addressBookModel.id}/edit"),
            HttpEntity(expected, headers()), AddressBookModel::class.java
        )

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.name).isEqualTo(expected.name)
    }

    @Test
    fun `verify delete`() {
        service.add(addressBookModel)

        val response = restTemplate.exchange(url("api/${addressBookModel.id}/delete"),
            HttpMethod.GET, HttpEntity(null, headers()), Unit::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(service.get(addressBookModel.id)).isNull()
    }
}
