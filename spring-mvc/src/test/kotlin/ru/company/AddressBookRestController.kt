package ru.company


import org.junit.jupiter.api.Assertions.assertEquals
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
import ru.company.model.Client
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressBookRestController {
    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(path: String): String {
        return "http://localhost:${port}/${path}"
    }

    fun addHeader(): HttpHeaders = HttpHeaders().also { it.add("Cookie", "auth=${LocalDateTime.now()}") }

    fun addClientForTest(headers: HttpHeaders) {
        val client = Client(1, "Ivan", "Address", "ex@em.ru")
        restTemplate.postForEntity(url("api/add"), HttpEntity(client, headers), Client::class.java)
    }

    @Test
    fun addClientTest() {
        val headers = addHeader()
        addClientForTest(headers)
        val client = Client(null, "Ivan", "Address", "ex@em.ru")
        val resp = restTemplate.postForEntity(url("api/add"), HttpEntity(client, headers), Client::class.java)
        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun getListTest() {
        val headers = addHeader()
        addClientForTest(headers)
        val response =
            restTemplate.exchange(url("api/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Client>::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(1, response.body?.size)
    }

    @Test
    fun viewTest() {
        val headers = addHeader()
        addClientForTest(headers)
        val resp =
            restTemplate.exchange(url("api/1/view"), HttpMethod.GET, HttpEntity(null, headers), Client::class.java)
        assertEquals(HttpStatus.OK, resp.statusCode)
        assertEquals("Ivan", resp.body?.fio)
    }

    @Test
    fun deleteTest() {
        val headers = addHeader()
        addClientForTest(headers)
        val resp =
            restTemplate.exchange(url("api/1/delete"), HttpMethod.DELETE, HttpEntity(null, headers), String::class.java)
        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun editClientTest() {
        val headers = addHeader()
        addClientForTest(headers)
        val client = Client(1, "Inna", "Address1", "ex@em.ru")
        val resp =
            restTemplate.exchange(url("api/1/edit"), HttpMethod.PUT, HttpEntity(client, headers), String::class.java)
        assertEquals(HttpStatus.OK, resp.statusCode)

        val response =
            restTemplate.exchange(url("api/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Client>::class.java)
        assertEquals("Inna", response.body?.get(0)!!.fio)
        assertEquals("Address1", response.body?.get(0)!!.address)
    }
}
