package ru.sber.springmvc.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import java.net.URI


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApiControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(s: String): String {
        return "http://localhost:${port}${s}"
    }

    @Test
    fun getUserListTest() {
        val resp = restTemplate.exchange(URI(url("/api/users")), HttpMethod.GET, createHttpEntity(), List::class.java)
        assertNotNull(resp)
        assertNotNull(resp.headers)
        assertTrue(resp.headers[HttpHeaders.CONTENT_TYPE]?.contains(MediaType.APPLICATION_JSON_VALUE) ?: false)
        assertNotNull(resp.body)
        assertTrue(resp.body is List<*>)
        assertEquals(1, resp.body?.size)
        val user = resp.body?.get(0)
        assertEquals("admin", (user as Map<*,*>)["login"])
    }

    @Test
    fun getUserTest() {
        val resp = restTemplate.exchange(URI(url("/api/users/1")), HttpMethod.GET, createHttpEntity(), Map::class.java)
        assertNotNull(resp)
        assertNotNull(resp.headers)
        assertTrue(resp.headers[HttpHeaders.CONTENT_TYPE]?.contains(MediaType.APPLICATION_JSON_VALUE) ?: false)
        assertNotNull(resp.body)
        assertTrue(resp.body is Map<*, *>)
        assertEquals("admin", resp.body?.get("login"))
    }

    fun createHttpEntity() : HttpEntity<Unit> {
        val headers = HttpHeaders()
        headers.add(HttpHeaders.COOKIE,"auth=0")
        return HttpEntity<Unit>(headers)
    }
}