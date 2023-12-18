package ru.sber.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import ru.sber.model.Person

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
class AddressBookRestControllerTest(
        @Autowired val restTemplate: TestRestTemplate
) {

    @Test
    fun listEntries() {
        val responseEntity: ResponseEntity<List<Person>> =
                restTemplate.exchange("/api/list", HttpMethod.GET, null)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
    }

    @Test
    fun getEntry() {
        val id = 5L

        val responseEntity: ResponseEntity<Person> =
                restTemplate.exchange("/api/$id/view", HttpMethod.GET, null)

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.statusCode)
    }

    @Test
    fun addEntry() {
        val entry = Person(3, "TEST", "TEST", "TEST")

        val responseEntity: ResponseEntity<Any> =
                restTemplate.exchange("/api/add", HttpMethod.POST, HttpEntity(entry))

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
    }

    @Test
    fun editEntry() {
        val id = 1L
        val entry = Person(1, "TEST", "TEST", "TEST")

        val responseEntity: ResponseEntity<Any> =
                restTemplate.exchange("/api/$id", HttpMethod.PUT, HttpEntity(entry))

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.statusCode)
    }

    @Test
    fun deleteEntry() {
        val id = 0L

        val responseEntity: ResponseEntity<Void> =
                restTemplate.exchange("/api/$id", HttpMethod.DELETE, null)

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.statusCode)
    }
}