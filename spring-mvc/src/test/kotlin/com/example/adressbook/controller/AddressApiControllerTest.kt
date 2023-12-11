package com.example.adressbook.controller

import com.example.adressbook.dto.AddressModel
import com.example.adressbook.dto.ResponseDTO
import com.example.adressbook.repository.AddressRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue


@RunWith(SpringJUnit4ClassRunner::class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressApiControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var repository: AddressRepository

    private val cookieHeader = HttpHeaders().apply {
        set("Cookie", "auth=${LocalDate.now().atStartOfDay()}")
    }

    private fun url(s: String) = "http://localhost:${port}/${s}"

    @Test
    fun addAddress() {
        val address = AddressModel(id = 1, name = "Иванов Иван Иванович", address = "Улица Пушкина, 2")

        val result = restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            HttpEntity(address, cookieHeader),
            object : ParameterizedTypeReference<ResponseDTO<AddressModel>>() {}
        )

        assertTrue(result.statusCode.is2xxSuccessful)
        assertEquals(address, result.body?.data)
    }

    @Test
    fun viewListOfAddresses() {
        // given
        val address = AddressModel(name = "Иванов Иван Иванович", address = "Улица Пушкина, 2").let {
            repository.saveAddress(it)
        }

        // when
        val result = restTemplate.exchange(
            url("/api/list"),
            HttpMethod.GET,
            HttpEntity<Any>(cookieHeader),
            object : ParameterizedTypeReference<ResponseDTO<List<AddressModel>>>() {}
        )

        // then
        assertTrue(result.statusCode.is2xxSuccessful)
        assertTrue(result.body?.data?.isNotEmpty() == true)
        assertEquals(address, result.body?.data?.first())
    }

    @Test
    fun viewAddress() {
        // given
        val address = AddressModel(name = "Иванов Иван Иванович", address = "Улица Пушкина, 2").let {
            repository.saveAddress(it)
        }

        // when
        val result = restTemplate.exchange(
            url("/api/${address?.id}"),
            HttpMethod.GET,
            HttpEntity<Any>(cookieHeader),
            object : ParameterizedTypeReference<ResponseDTO<AddressModel>>() {}
        )

        // then
        assertTrue(result.statusCode.is2xxSuccessful)
        assertEquals(address, result.body?.data)
    }

    @Test
    fun editAddress() {
        // given
        val address = repository.saveAddress(AddressModel(name = "Иванов Иван Иванович", address = "Улица Пушкина, 2"))!!
        val modifiedAddress = address.copy(
            name = "Петров Петр Петрович"
        )

        // when
        val result = restTemplate.exchange(
            url("/api/${address.id}/edit"),
            HttpMethod.PUT,
            HttpEntity(modifiedAddress, cookieHeader),
            object : ParameterizedTypeReference<ResponseDTO<AddressModel>>() {}
        )

        // then
        assertTrue(result.statusCode.is2xxSuccessful)
        assertEquals(modifiedAddress, result.body?.data)
    }

    @Test
    fun deleteAddress() {
        // given
        val address = AddressModel(name = "Иванов Иван Иванович", address = "Улица Пушкина, 2").let {
            repository.saveAddress(it)
        }

        // when
        val result = restTemplate.exchange<Unit>(
            url("/api/${address?.id}/delete"),
            HttpMethod.DELETE,
            HttpEntity<Any>(cookieHeader)
        )

        // then
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNull(repository.getAddressById(address!!.id))
    }
}