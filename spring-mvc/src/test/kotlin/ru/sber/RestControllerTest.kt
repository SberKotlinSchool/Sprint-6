package ru.sber

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.*
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
import ru.sber.dto.Address
import ru.sber.repository.AddressBook
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class RestControllerTest {

    @LocalServerPort
    private var port = 0
    @Autowired
    private var addressBook = AddressBook()
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    fun createHeader(): HttpHeaders{

        var dataTime = LocalDateTime.now().minusMinutes(5)
        var headers = HttpHeaders()
        headers.add( HttpHeaders.COOKIE, "auth=$dataTime")
        return headers
    }
    @Test
    fun list() {

        addressBook.add(Address("Name1", "Email1" ) )
        addressBook.add(Address("Name2", "Email2" ) )


        val resp = restTemplate
            .exchange(/* url = */ "http://localhost:${port}/api/list",
                /* method = */ HttpMethod.GET,
                /* requestEntity = */ HttpEntity(null, createHeader()),
                /* responseType = */ object : ParameterizedTypeReference< List<Pair<Int, Address>>>() {})

        assertEquals(2, resp.body?.size)
        assertEquals(HttpStatus.OK, resp.statusCode)

    }

    @Test
    fun add() {

        val address = Address("Name1", "Email1")
        val resp = restTemplate
            .exchange(/* url = */ "http://localhost:${port}/api/add",
                /* method = */ HttpMethod.POST,
                /* requestEntity = */ HttpEntity(address, createHeader()),
                /* responseType = */ Address::class.java )

        assertEquals(HttpStatus.OK, resp.statusCode)

    }

    @Test
    fun view() {

        addressBook.add(Address("Name1", "Email1"))

        val id = 0

        val resp = restTemplate
            .exchange(/* url = */ "http://localhost:${port}/api/0/view",
                /* method = */ HttpMethod.GET,
                /* requestEntity = */ HttpEntity(null, createHeader()),
                /* responseType = */ Address::class.java )

        assertEquals(HttpStatus.OK, resp.statusCode)

    }

    @Test
    fun delete() {

        addressBook.add(Address("Name1", "Email1"))

        val resp = restTemplate
            .exchange(/* url = */ "http://localhost:${port}/api/0/delete",
                /* method = */ HttpMethod.DELETE,
                /* requestEntity = */ HttpEntity(null, createHeader()),
                /* responseType = */ Address::class.java )

        assertEquals(HttpStatus.OK, resp.statusCode)

    }

    @Test
    fun edit() {

        addressBook.add(Address("Name1", "Email1"))
        val addressChange = Address("Name2", "Email2")

        val resp = restTemplate
            .exchange(/* url = */ "http://localhost:${port}/api/0/edit",
                /* method = */ HttpMethod.PUT,
                /* requestEntity = */ HttpEntity(addressChange, createHeader()),
                /* responseType = */ Address::class.java )

        assertEquals(HttpStatus.OK, resp.statusCode)
        assertEquals(addressChange, resp.body!!)

    }
}