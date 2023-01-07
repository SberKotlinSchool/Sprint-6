package ru.sber.springmvc.controller

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import ru.sber.springmvc.model.Address
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class AddressRestControllerTest {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @LocalServerPort
    private var port: Int = 0

    private val testAddress = Address(name = "Kevin", city = "Scampton", phone = "112")

    @Test
    @Order(1)
    fun `requests to url without cookie authentication redirect to form _login`() {
        val response = restTemplate.exchange(
            url("rest/list"),
            HttpMethod.GET,
            HttpEntity(null, null),
            String::class.java
        )

        assertTrue { response.statusCode == HttpStatus.OK }
        assertTrue { response.body.toString().contains("login") }
        assertTrue { response.body.toString().contains("password") }
    }

    @Test
    @Order(2)
    fun `method get to _rest_list with cookie authentication return empty list`() {
        val response = sendGetToRestList(authenticationHeader())

        assertTrue { response.statusCode == HttpStatus.OK }
        assertTrue { response.body?.isEmpty()!! }
    }

    @Test
    @Order(3)
    fun `method post to _rest_add with cookie authentication should add new address`() {
        val responsePost = restTemplate.exchange(
            url("rest"),
            HttpMethod.POST,
            HttpEntity(testAddress, authenticationHeader()),
            Unit::class.java
        )

        val responseGet = sendGetToRestList(authenticationHeader())

        assertTrue { responsePost.statusCode == HttpStatus.OK }
        assertTrue { responseGet.body!!.isNotEmpty() }
        assertTrue { responseGet.body!![0].equalsAddress(testAddress) }
    }

    @Test
    @Order(4)
    fun `method get to _rest_id_view with cookie authentication should return address json`() {
        val response = restTemplate.exchange(
            url("rest/1"),
            HttpMethod.GET,
            HttpEntity(null, authenticationHeader()),
            Address::class.java
        )

        assertTrue { response.statusCode == HttpStatus.OK }
        assertTrue { response.body!!.equalsAddress(testAddress) }
    }

    @Test
    @Order(5)
    fun `method post to _rest_id_edit with cookie authentication should change address`() {
        val editAddress = testAddress.copy(name = "Oscar")
        val responsePost = restTemplate.exchange(
            url("rest/1"),
            HttpMethod.PUT,
            HttpEntity(editAddress, authenticationHeader()),
            Unit::class.java
        )
        assertTrue { responsePost.statusCode == HttpStatus.OK }

        val responseGet = sendGetToRestList(authenticationHeader())

        assertTrue { responseGet.body!![0].name == "Oscar" }

    }

    @Test
    @Order(6)
    fun `method post to _rest_id_delete with cookie authentication should remove address`() {
        val responsePost = restTemplate.exchange(
            url("rest/1"),
            HttpMethod.DELETE,
            HttpEntity(null, authenticationHeader()),
            Unit::class.java
        )
        assertTrue { responsePost.statusCode == HttpStatus.OK }

        val responseGet = sendGetToRestList(authenticationHeader())

        assertTrue { responseGet.body!!.isEmpty() }
    }

    private fun url(location: String) = "http://localhost:$port/$location"

    private fun authenticationHeader() = HttpHeaders().apply { add("Cookie", "authentication=${LocalDateTime.now()}") }

    private fun sendGetToRestList(headers: HttpHeaders) = restTemplate.exchange(
        url("rest/list"),
        HttpMethod.GET,
        HttpEntity(null, headers),
        Array<Address>::class.java
    ).also { assertTrue { it.statusCode == HttpStatus.OK } }

    private fun Address.equalsAddress(address: Address): Boolean {
        return this.name == address.name && this.city == address.city && this.phone == address.phone
    }
}
