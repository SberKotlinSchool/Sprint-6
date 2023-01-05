package ru.sber.mvc

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.net.URL

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationServletTest {

    @LocalServerPort
    private var currentPort: Int = 0

    private fun url(path: String): String =
        URL("http", "localhost", currentPort, path).toString()

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `test login open`() {
        val request = HttpEntity(null, HttpHeaders())

        val response = restTemplate.exchange(url("/login"), HttpMethod.GET, request, String::class.java)

        response.run {
            Assertions.assertEquals(HttpStatus.OK, statusCode)
            Assertions.assertNotNull(body)
        }
    }
}
