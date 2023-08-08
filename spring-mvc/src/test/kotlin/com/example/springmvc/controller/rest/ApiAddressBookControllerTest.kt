package com.example.springmvc.controller.rest

import com.example.springmvc.model.Contact
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ApiAddressBookControllerTest {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    var headers = HttpHeaders()

    val correctId = "1"
    val correctName = "Jennifer"

    val incorrectId = "10"
    val correctPhone = "333-33-33"

    @BeforeEach
    fun beforeEach() {
        testRestTemplate.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
        headers.apply {
            contentType = MediaType.APPLICATION_JSON
            add(HttpHeaders.COOKIE, "auth=${LocalDateTime.now()}")
        }
    }

    @Test
    fun authenticationFailTest() {
        val request = RequestEntity
            .get("/api/list")
            .build()

        val response = testRestTemplate
            .exchange(request, Unit::class.java)

        assert(response.statusCode == HttpStatus.UNAUTHORIZED)
    }

    @Test
    fun listAllContactsTest() {
        val request = RequestEntity.get("/api/list")
            .headers(headers)
            .build()

        val response = testRestTemplate.exchange(request, Array<Contact>::class.java)

        assertAll (
            { assert(response.statusCode == HttpStatus.OK) },
            { assert(response.body?.size == 2) },
            { assert(response.body?.get(1) == Contact(2, "Kate", "222-22-22")) }
        )
    }

    @Test
    fun viewContactSuccessTest() {
        val request = RequestEntity
            .get("/api/${correctId}/view")
            .headers(headers)
            .build()

        val response = testRestTemplate.exchange(request, Contact::class.java)

        assertAll (
            { assert(response.statusCode == HttpStatus.OK) },
            { assert(response.body == Contact(1, "Tom", "111-11-11")) }
        )
    }

    @Test
    fun viewContactFailTest() {
        val request = RequestEntity
            .get("/api/${incorrectId}/view")
            .headers(headers)
            .build()

        val response = testRestTemplate.exchange(request, Contact::class.java)

        assert(response.statusCode == HttpStatus.NOT_FOUND)
    }

    @Test
    fun editContactSuccessTest() {
        val request = RequestEntity
            .patch("/api/${correctId}/edit")
            .headers(headers)
            .body(Contact(id = 1, name = correctName, phone = correctPhone))

        val response = testRestTemplate.exchange(request, Unit::class.java)

        assert(response.statusCode == HttpStatus.ACCEPTED)
    }

    @Test
    fun editContactNotFoundTest() {
        val request = RequestEntity
            .patch("/api/${incorrectId}/edit")
            .headers(headers)
            .body(Contact(id = 10, name = correctName, phone = correctPhone))

        val response = testRestTemplate.exchange(request, Unit::class.java)

        assert(response.statusCode == HttpStatus.NOT_FOUND)
    }

    @Test
    fun editContactBadRequestTest() {
        val jsonBody = """{"id": "1","name": null,"phone": null}"""

        val request = RequestEntity
            .patch("/api/${correctId}/edit")
            .headers(headers)
            .body(jsonBody)

        val response = testRestTemplate.exchange(request, Unit::class.java)

        assert(response.statusCode == HttpStatus.BAD_REQUEST)
    }

    @Test
    fun deleteContactSuccessTest() {
        val request = RequestEntity
            .delete("/api/${correctId}/delete")
            .headers(headers)
            .build()

        val response = testRestTemplate.exchange(request, Unit::class.java)

        assert(response.statusCode == HttpStatus.ACCEPTED)
    }

    @Test
    fun deleteContactNotFoundTest() {
        val request = RequestEntity
            .delete("/api/${incorrectId}/delete")
            .headers(headers)
            .build()

        val response = testRestTemplate.exchange(request, Unit::class.java)

        assert(response.statusCode == HttpStatus.NOT_FOUND)
    }

    @Test
    fun addContactSuccessTest() {
        val request = RequestEntity
            .post("/api/add")
            .headers(headers)
            .body(Contact(name = "James", phone = "333-33-33"))

        val response = testRestTemplate.exchange(request, Unit::class.java)

        assert(response.statusCode == HttpStatus.ACCEPTED)
    }

    @Test
    fun addContactBadRequestTest() {
        val jsonBody = """{"name": null,"phone": null}"""

        val request = RequestEntity
            .post("/api/add")
            .headers(headers)
            .body(jsonBody)

        val response = testRestTemplate.exchange(request, Unit::class.java)

        assert(response.statusCode == HttpStatus.BAD_REQUEST)
    }
}
