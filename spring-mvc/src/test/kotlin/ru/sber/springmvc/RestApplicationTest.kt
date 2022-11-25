package ru.sber.springmvc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApplicationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    @Test
    fun testAuthorSuccess() {
        var resp = restTemplate.getForObject<String>(url("/api/message/author?name=admin"))
        assertThat(resp).contains("Hello")
        resp = restTemplate.getForObject<String>(url("/api/message/author?name=user"))
        assertThat(resp).contains("Hello")
    }

    @Test
    fun testAuthorFail() {
        var resp = restTemplate.getForObject<String>(url("/api/message/author?name=noname"))
        assertThat(resp).contains("[]")
    }
}