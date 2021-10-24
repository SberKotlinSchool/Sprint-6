package ru.sber.mvc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import ru.sber.mvc.entity.AddressBook
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class MvcApplicationTests {
    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private var headers: HttpHeaders = HttpHeaders()

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    @BeforeEach
    fun setUpCookie() {
        val cookie = login()
        headers.add("Cookie", cookie)

        val address = AddressBook()
        address.setId(0)
        address.setName("Pavel")
        address.setAddress("London")
        val resp = restTemplate.postForEntity(url("api/add"),
            HttpEntity(address, headers),
            AddressBook::class.java)
    }

    fun login(): String {
        val request = LinkedMultiValueMap<String, String>()
        request["login"] = "login1"
        request["password"] = "password1"

        val resp = restTemplate.postForEntity(url("login"),
            HttpEntity(request, HttpHeaders()),
            String::class.java)

        return resp.headers["Set-Cookie"]!!.get(0)
    }

    @Test
    fun testAddAddress() {
        val address = AddressBook()
        address.setId(0)
        address.setName("Fedor")
        address.setAddress("Moscow")
        val resp = restTemplate.postForEntity(url("api/add"),
            HttpEntity(address, headers),
            AddressBook::class.java)

        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun testGetList() {
        val resp = restTemplate.exchange(url("api/list"),
            HttpMethod.GET,
            HttpEntity(null, headers),
            String::class.java
        )

        assertEquals(HttpStatus.OK, resp.statusCode)
        assertThat(resp.body).contains("Pavel")
    }

    @Test
    fun testView() {
        val resp = restTemplate.exchange(url("api/0/view"),
            HttpMethod.GET,
            HttpEntity(null, headers),
            AddressBook::class.java
        )

        assertEquals(HttpStatus.OK, resp.statusCode)
        assertEquals("London", resp.body.getAddress())
    }

    @Test
    fun testDelete() {
        val resp = restTemplate.exchange(url("api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, headers),
            String::class.java
        )

        assertEquals(HttpStatus.OK, resp.statusCode)
    }
}
