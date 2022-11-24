package ru.sber.kotlinmvc.controllers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import ru.sber.kotlinmvc.entities.Client
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class RestControllerTest {
    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(path: String): String {
        return "http://localhost:${port}/${path}"
    }

    fun addHeader(): HttpHeaders = HttpHeaders().also { it.add("Cookie", "auth=${LocalDateTime.now()}") }

    fun addClientForTest(headers: HttpHeaders) {
        val client = Client("Ivan", "Address", "123456", "ex@em.ru").also { it.id = Integer(1) }
        restTemplate.postForEntity(url("rest/add"), HttpEntity(client, headers), Client::class.java)
    }

    @Test
    fun addClientTest() {
        val headers = addHeader()
        addClientForTest(headers)
        val client = Client("Ivan", "Address", "123456", "ex@em.ru").also { it.id = Integer(1) }
        val resp = restTemplate.postForEntity(url("rest/add"), HttpEntity(client, headers), Client::class.java)
        Assertions.assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun getListTest() {
        val headers = addHeader()
        addClientForTest(headers)
        val response =
            restTemplate.exchange(url("rest/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Client>::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(1, response.body?.size)
    }

    @Test
    fun viewTest() {
        val headers = addHeader()
        addClientForTest(headers)
        val resp =
            restTemplate.exchange(url("rest/1/view"), HttpMethod.GET, HttpEntity(null, headers), Client::class.java)
        Assertions.assertEquals(HttpStatus.OK, resp.statusCode)
        Assertions.assertEquals("Ivan", resp.body?.name)
    }

    @Test
    fun deleteTest() {
        val headers = addHeader()
        addClientForTest(headers)
        val resp =
            restTemplate.exchange(url("rest/1/delete"), HttpMethod.DELETE, HttpEntity(null, headers), String::class.java)
        Assertions.assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun editClientTest() {
        val headers = addHeader()
        addClientForTest(headers)
        val client = Client("Ivan", "Address", "000-000", "ex@em.ru").also { it.id = 2 as Integer }
        val resp =
            restTemplate.exchange(url("rest/2/edit"), HttpMethod.PUT, HttpEntity(client, headers), String::class.java)
        Assertions.assertEquals(HttpStatus.OK, resp.statusCode)

        val response =
            restTemplate.exchange(url("rest/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Client>::class.java)
        Assertions.assertEquals("Ivan", response.body?.get(0)!!.name)
        Assertions.assertEquals("Address", response.body?.get(0)!!.address)
    }


}