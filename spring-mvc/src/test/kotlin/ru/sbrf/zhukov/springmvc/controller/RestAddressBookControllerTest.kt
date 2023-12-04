package ru.sbrf.zhukov.springmvc.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import ru.sbrf.zhukov.springmvc.data.AddressEntry


import org.junit.jupiter.api.Assertions.*
import org.springframework.http.HttpEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
internal class RestAddressBookControllerIntegrationTest(
    @Autowired val restTemplate: TestRestTemplate
) {

    @Test
    fun listEntries() {
        val responseEntity: ResponseEntity<List<AddressEntry>> =
            restTemplate.exchange("/api/list", HttpMethod.GET, null)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
    }

    @Test
    fun getEntry() {
        val id = 1L
        val responseEntity: ResponseEntity<AddressEntry> =
            restTemplate.exchange("/api/$id", HttpMethod.GET, null)

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.statusCode)
    }

    @Test
    fun addEntry() {
        val entry = AddressEntry(0, "John Doe", "123 Main St")

        val responseEntity: ResponseEntity<AddressEntry> =
            restTemplate.exchange("/api/add", HttpMethod.POST, HttpEntity(entry))

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
    }

    @Test
    fun editEntry() {
        val id = 1L
        val entry = AddressEntry(id, "Edited Person", "789 Edited St")

        val responseEntity: ResponseEntity<AddressEntry> =
            restTemplate.exchange("/api/$id", HttpMethod.PUT, HttpEntity(entry))

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.statusCode)
    }

    @Test
    fun deleteEntry() {
        val id = 1L

        val responseEntity: ResponseEntity<Void> =
            restTemplate.exchange("/api/$id", HttpMethod.DELETE, null)

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.statusCode)
    }
}