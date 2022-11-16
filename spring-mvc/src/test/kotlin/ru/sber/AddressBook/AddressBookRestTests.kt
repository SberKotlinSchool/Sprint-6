package ru.sber.AddressBook

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import ru.sber.AddressBook.Model.CustomerModel
import ru.sber.AddressBook.Model.ResponseDescription
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressBookRestTests() {
    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(path: String) = "http://localhost:$port/$path"

    private fun setCookieInHeader(): HttpHeaders {
        val cookie =  HttpHeaders()
        cookie.add(
            "Cookie",
            "auth=${LocalDateTime.now().plusMinutes(10)}"
        )
        return cookie
    }

    private val testCustomer = CustomerModel(
        firstName = "FirstName1",
        lastName = "LastName1",
        middleName = "MiddleName1",
        phone = "+79234622135",
        email = null,
        address = "address"
    )

    @Test
    fun `test should redirect to Login Page if cookie not found`() {

        val response = restTemplate.getForObject(
            url("/api/list"),
            String::class.java
        )
        println(response)

        assertTrue(
            response.contains("<title>Login</title>")
        )
    }

    @Test
    fun `get list`() {
        val response = restTemplate.exchange(
            url("api/list"),
            HttpMethod.GET,
            HttpEntity(null, setCookieInHeader()),
            CustomerModel::class.java
        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertTrue(response.hasBody())
    }

    @Test
    fun `post add row`() {
        val response = restTemplate.postForEntity(
            url("api/add"),
            HttpEntity(testCustomer, setCookieInHeader()),
            ResponseDescription::class.java
        )

        assert(response.statusCode.is2xxSuccessful)
        assert(response.body?.result == "OK")
        assert(response.body?.description == "Added row")
    }

    @Test
    fun `put edit row`() {

        val response = restTemplate.exchange(
            url("api/edit/1"),
            HttpMethod.PUT,
            HttpEntity(testCustomer, setCookieInHeader()),
            ResponseDescription::class.java
        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertTrue(response.body?.result == "OK")
        assertTrue(response.body?.description == "Edited row for client 1")
    }

    @Test
    fun `get view row`() {
        val response = restTemplate.exchange(
            url("api/view?id=1"),
            HttpMethod.GET,
            HttpEntity(null, setCookieInHeader()),
            CustomerModel::class.java
        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertTrue(
            response.body?.lastName == "LastName1" && response.body?.firstName == "FirstName1"
        )

    }

    @Test
    fun `delete delete row`() {
        val response = restTemplate.exchange(
            url("api/delete/1"),
            HttpMethod.DELETE,
            HttpEntity(null, setCookieInHeader()),
            ResponseDescription::class.java
        )

        assertTrue(response.statusCode.is2xxSuccessful)
        assertTrue(response.body?.result == "OK")
        assertTrue(response.body?.description == "Deleted 1")
    }

}