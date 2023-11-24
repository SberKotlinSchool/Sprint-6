package com.firebat.addressbook.controller

import com.firebat.addressbook.model.Entry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RestControllerTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun addEntry() {
        restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            HttpEntity(Entry(name = "Name", address = "Address"), getAuthHeader()),
            Long::class.java
        ).apply {
            assertEquals(1, this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
        }
    }

    @Test
    fun addEntryWithoutAuth() {
        restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            HttpEntity(Entry(name = "Name", address = "Address"), null),
            Long::class.java
        ).apply {
            assertEquals(HttpStatus.FOUND, this.statusCode)
        }
    }

    @Test
    fun getEntries() {
        restTemplate.exchange(
            url("/api/list"),
            HttpMethod.GET,
            HttpEntity(
                null,
                getAuthHeader()
            ), Set::class.java
        ).apply {
            assertNotNull(this)
            assertNotNull(this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
            assertEquals(1, this.body!!.size)
        }
    }

    @Test
    fun getEntriesWithoutAuth() {
        restTemplate.exchange(
            url("/api/list"),
            HttpMethod.POST,
            HttpEntity(
                null,
                null,
            ), Set::class.java
        ).apply {
            assertEquals(HttpStatus.FOUND, this.statusCode)
        }
    }

    @Test
    fun viewEntry() {
        restTemplate.exchange(
            url("/api/0/view"),
            HttpMethod.GET,
            HttpEntity(null, getAuthHeader()),
            Entry::class.java
        ).apply {
            assertNotNull(this)
            assertNotNull(this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
            assertEquals("InitialName", this.body!!.name)
            assertEquals("InitialAddress", this.body!!.address)
        }
    }

    @Test
    fun viewNonExistingEntry() {
        restTemplate.exchange(
            url("/api/2/view"),
            HttpMethod.GET,
            HttpEntity(null, getAuthHeader()),
            Entry::class.java
        ).apply {
            assertEquals(HttpStatus.NOT_FOUND, this.statusCode)
        }
    }

    @Test
    fun viewEntryWithoutAuth() {
        restTemplate.exchange(
            url("/api/0/view"),
            HttpMethod.POST,
            HttpEntity(null, null),
            Entry::class.java
        ).apply {
            assertEquals(HttpStatus.FOUND, this.statusCode)
        }
    }

    @Test
    fun editEntry() {
        restTemplate.exchange(
            url("/api/0/edit"),
            HttpMethod.PUT,
            HttpEntity(Entry(name = "Name2", address = "Address2"), getAuthHeader()),
            Entry::class.java
        ).apply {
            assertNotNull(this)
            assertNotNull(this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
        }
    }

    @Test
    fun editNonExistingEntry() {
        restTemplate.exchange(
            url("/api/2/edit"),
            HttpMethod.PUT,
            HttpEntity(Entry(name = "Name2", address = "Address2"), getAuthHeader()),
            Entry::class.java
        ).apply {
            assertEquals(HttpStatus.NOT_FOUND, this.statusCode)
        }
    }

    @Test
    fun editEntryWithoutAuth() {
        restTemplate.exchange(
            url("/api/0/edit"),
            HttpMethod.PUT,
            HttpEntity(Entry(name = "Name2", address = "Address2"), null),
            Entry::class.java
        ).apply {
            assertEquals(HttpStatus.FOUND, this.statusCode)
        }
    }

    @Test
    fun deleteEntry() {
        restTemplate.exchange(
            url("/api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, getAuthHeader()),
            Unit::class.java
        ).apply {
            assertEquals(HttpStatus.OK, this.statusCode)
        }
    }

    @Test
    fun deleteNonExistingEntry() {
        restTemplate.exchange(
            url("/api/2/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, getAuthHeader()),
            Unit::class.java
        ).apply {
            assertEquals(HttpStatus.NOT_FOUND, this.statusCode)
        }
    }

    @Test
    fun deleteEntryWithoutAuth() {
        restTemplate.exchange(
            url("/api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, null),
            Unit::class.java
        ).apply {
            assertEquals(HttpStatus.FOUND, this.statusCode)
        }
    }

    private fun url(path: String) = "http://localhost:${port}/${path}"

    private fun getAuthHeader(): HttpHeaders =
        HttpHeaders().also { it.add("Cookie", "auth1=${LocalDateTime.now().plusMinutes(5)}") }
}