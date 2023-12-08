package ru.sber.controller

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
import ru.sber.model.BaseEntity
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RestControllerTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun addBaseEntity() {
        restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            HttpEntity(BaseEntity(name = "Name", address = "Address"), getAuthHeader()),
            Long::class.java
        ).apply {
            assertEquals(1, this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
        }
    }

    @Test
    fun addBaseEntityWithoutAuth() {
        restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            HttpEntity(BaseEntity(name = "Name", address = "Address"), null),
            Long::class.java
        ).apply {
            assertEquals(HttpStatus.FOUND, this.statusCode)
        }
    }

    @Test
    fun getBaseEntities() {
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
    fun getBaseEntitiesWithoutAuth() {
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
    fun viewBaseEntity() {
        restTemplate.exchange(
            url("/api/0/view"),
            HttpMethod.GET,
            HttpEntity(null, getAuthHeader()),
            BaseEntity::class.java
        ).apply {
            assertNotNull(this)
            assertNotNull(this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
            assertEquals("FirstName", this.body!!.name)
            assertEquals("Moscow, Lenin str, 1", this.body!!.address)
        }
    }

    @Test
    fun viewNonExistingBaseEntity() {
        restTemplate.exchange(
            url("/api/2/view"),
            HttpMethod.GET,
            HttpEntity(null, getAuthHeader()),
            BaseEntity::class.java
        ).apply {
            assertEquals(HttpStatus.NOT_FOUND, this.statusCode)
        }
    }

    @Test
    fun viewBaseEntityWithoutAuth() {
        restTemplate.exchange(
            url("/api/0/view"),
            HttpMethod.POST,
            HttpEntity(null, null),
            BaseEntity::class.java
        ).apply {
            assertEquals(HttpStatus.FOUND, this.statusCode)
        }
    }

    @Test
    fun editBaseEntity() {
        restTemplate.exchange(
            url("/api/0/edit"),
            HttpMethod.PUT,
            HttpEntity(BaseEntity(name = "Name2", address = "Address2"), getAuthHeader()),
            BaseEntity::class.java
        ).apply {
            assertNotNull(this)
            assertNotNull(this.body)
            assertEquals(HttpStatus.OK, this.statusCode)
        }
    }

    @Test
    fun editNonExistingBaseEntity() {
        restTemplate.exchange(
            url("/api/2/edit"),
            HttpMethod.PUT,
            HttpEntity(BaseEntity(name = "Name2", address = "Address2"), getAuthHeader()),
            BaseEntity::class.java
        ).apply {
            assertEquals(HttpStatus.NOT_FOUND, this.statusCode)
        }
    }

    @Test
    fun editBaseEntityWithoutAuth() {
        restTemplate.exchange(
            url("/api/0/edit"),
            HttpMethod.PUT,
            HttpEntity(BaseEntity(name = "Name2", address = "Address2"), null),
            BaseEntity::class.java
        ).apply {
            assertEquals(HttpStatus.FOUND, this.statusCode)
        }
    }

    @Test
    fun deleteBaseEntity() {
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
    fun deleteNonExistingBaseEntity() {
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
    fun deleteBaseEntityWithoutAuth() {
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