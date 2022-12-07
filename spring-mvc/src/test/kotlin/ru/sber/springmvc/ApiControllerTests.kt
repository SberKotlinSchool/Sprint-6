package ru.sber.springmvc

import org.junit.jupiter.api.Assertions
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
import ru.sber.springmvc.dto.Entry
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApiControllerTests {

    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(path: String) = "http://localhost:$port/$path"

    private fun headers() = HttpHeaders().apply {
        add("Cookie", "auth=${LocalDateTime.now()}")
    }

    @Test
    fun add() {
        // Arrange
        val headers = headers()
        val entry = Entry("name", "phoneNumber")

        // Act
        val response = restTemplate.postForEntity(url("api/add"), HttpEntity(entry, headers), Entry::class.java)

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun addWithoutAuthorize() {
        // Arrange
        val entry = Entry("name", "phoneNumber")

        // Act
        val response = restTemplate.postForEntity(url("api/add"), HttpEntity(entry, null), Entry::class.java)

        // Assert
        Assertions.assertEquals(HttpStatus.FOUND, response.statusCode)
    }

    @Test
    fun listEmpty() {
        // Arrange
        val headers = headers()

        // Act
        val response = restTemplate.exchange(url("api/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Entry>::class.java)

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals(0, response.body?.size)
    }

    @Test
    fun listWithData() {
        // Arrange
        val headers = headers()
        val entry = Entry("name", "phoneNumber")
        val entry2 = Entry("name2", "phoneNumber2")

        // Act
        restTemplate.postForEntity(url("api/add"), HttpEntity(entry, headers), Entry::class.java)
        restTemplate.postForEntity(url("api/add"), HttpEntity(entry2, headers), Entry::class.java)
        restTemplate.postForEntity(url("api/add"), HttpEntity(entry, headers), Entry::class.java)
        val response = restTemplate.exchange(url("api/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Entry>::class.java)

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals(2, response.body?.size)
    }

    @Test
    fun view() {
        // Arrange
        val headers = headers()
        val entry = Entry("name", "phoneNumber")

        // Act
        restTemplate.postForEntity(url("api/add"), HttpEntity(entry, headers), Entry::class.java)
        val response = restTemplate.exchange(url("api/${entry.name}/view"), HttpMethod.GET, HttpEntity(null, headers), Unit::class.java)

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun viewMissing() {
        // Arrange
        val headers = headers()

        // Act
        val response = restTemplate.exchange(url("api/test/view"), HttpMethod.GET, HttpEntity(null, headers), Unit::class.java)

        // Assert
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun edit() {
        // Arrange
        val headers = headers()
        val entry = Entry("name", "phoneNumber")
        val newEntry = Entry("newName", "newPhoneNumber")

        // Act
        restTemplate.postForEntity(url("api/add"), HttpEntity(entry, headers), Entry::class.java)
        val response = restTemplate.exchange(url("api/${entry.name}/edit"), HttpMethod.POST, HttpEntity(newEntry, headers), Entry::class.java)
        val list = restTemplate.exchange(url("api/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Entry>::class.java).body

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(newEntry, list?.first())
    }

    @Test
    fun editMissing() {
        // Arrange
        val headers = headers()
        val entry = Entry("name", "phoneNumber")
        val newEntry = Entry("newName", "newPhoneNumber")

        // Act
        restTemplate.postForEntity(url("api/add"), HttpEntity(entry, headers), Entry::class.java)
        val response = restTemplate.exchange(url("api/test/edit"), HttpMethod.POST, HttpEntity(newEntry, headers), Entry::class.java)
        val list = restTemplate.exchange(url("api/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Entry>::class.java).body

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(2, list?.size)
        Assertions.assertEquals(true, list?.contains(entry))
        Assertions.assertEquals(true, list?.contains(newEntry))
    }

    @Test
    fun delete() {
        // Arrange
        val headers = headers()
        val entry = Entry("name", "phoneNumber")

        // Act
        restTemplate.postForEntity(url("api/add"), HttpEntity(entry, headers), Entry::class.java)
        val response = restTemplate.exchange(url("api/${entry.name}/delete"), HttpMethod.DELETE, HttpEntity(null, headers), Unit::class.java)
        val list = restTemplate.exchange(url("api/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Entry>::class.java).body

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, list?.size)
    }

    @Test
    fun deleteMissing() {
        // Arrange
        val headers = headers()

        // Act
        val response = restTemplate.exchange(url("api/test/delete"), HttpMethod.DELETE, HttpEntity(null, headers), Unit::class.java)
        val list = restTemplate.exchange(url("api/list"), HttpMethod.GET, HttpEntity(null, headers), Array<Entry>::class.java).body

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, list?.size)
    }
}