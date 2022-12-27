package ru.sber.addressbook

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDateTime
import ru.sber.addressbook.models.*
import org.junit.jupiter.api.Assertions
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressbookRestControllerTest {

    @LocalServerPort
    private val port = 8080

    @Autowired
    private lateinit var rests: TestRestTemplate

    private fun url(urls: String) = "http://localhost:$port/$urls"

    private fun headers() = HttpHeaders().apply {
        add("Cookie", "auth=${LocalDateTime.now()}")
    }

    @Test
    fun `without authentication test`() {
        val response = rests.exchange(url("api/list"), HttpMethod.GET,
                                                    HttpEntity(null, null),
                                                    Unit::class.java)
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    }

    @Test
    fun `best add test`() {
        val client1 =  CLientsInfo(name = "Иван", phone = "8-999-999-99-99")
        val response = rests.postForEntity(url("api/add"), HttpEntity(client1, headers()), CLientsInfo::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `list without clients test`() {
        val response = rests.exchange(url("api/list"), HttpMethod.GET,
                                                            HttpEntity(null, headers()),
                                                            Array<CLientsInfo>::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
        response.body?.let { Assertions.assertEquals(0, it.size) }

    }

    @Test
    fun `list with clients test`() {
        val client1 =  CLientsInfo(name = "Иван", phone = "8-999-999-99-99")
        rests.postForEntity(url("api/add"), HttpEntity(client1, headers()), CLientsInfo::class.java)
        val response = rests.exchange(url("api/list"),
                                        HttpMethod.GET,
                                        HttpEntity(null, headers()),
                                        Array<CLientsInfo>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
        response.body?.let { Assertions.assertEquals(1, it.size) }
    }

    @Test
    fun `view client test`() {
        val client1 =  CLientsInfo(name = "Иван", phone = "8-999-999-99-99")
        rests.postForEntity(url("api/add"), HttpEntity(client1, headers()), CLientsInfo::class.java)
        val response = rests.exchange(url("api/${client1.name}/view"),
                                            HttpMethod.GET,
                                            HttpEntity(null, headers()), Unit::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

    }

    @Test
    fun `edit clients test`() {
        val client1 =  CLientsInfo(name = "Иван", phone = "8-999-999-99-99")
        val client2 = CLientsInfo(name = "Петр", phone = "8-888-888-88-88")
        rests.postForEntity(url("api/add"), HttpEntity(client1, headers()), CLientsInfo::class.java)
        val response = rests.exchange(url("api/${client1.name}/edit"),
                                        HttpMethod.POST,
                                        HttpEntity(client2, headers()), CLientsInfo::class.java)

        val response2 = rests.exchange(url("api/list"),
                                        HttpMethod.GET,
                                        HttpEntity(null, headers()),
                                        Array<CLientsInfo>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        response2.body?.let { Assertions.assertEquals(client2, it.first()) }
    }

    @Test
    fun `delete clients test`() {
        val client1 =  CLientsInfo(name = "Иван", phone = "8-999-999-99-99")
        rests.postForEntity(url("api/add"), HttpEntity(client1, headers()), CLientsInfo::class.java)
        val response = rests.exchange(url("api/${client1.name}/delete"),
                                        HttpMethod.DELETE,
                                        HttpEntity(null, headers()), Unit::class.java)

        val response2 = rests.exchange(url("api/list"),
                                        HttpMethod.GET,
                                        HttpEntity(null, headers()),
                                        Array<CLientsInfo>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        response2.body?.let { Assertions.assertEquals(0, it.size) }
    }


}