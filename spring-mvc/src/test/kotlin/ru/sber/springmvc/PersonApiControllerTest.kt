package ru.sber.springmvc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext
import ru.sber.springmvc.model.Person
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonApiControllerTest {

    companion object {
        private const val NAME = "Иванов Иван"
        private const val ADDRESS = "Москва"
        private const val PHONE = "1234567890"
    }

    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun addPersonTestSuccessful() {
        val actualResponse = addTestPerson()
        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        val actualPerson = actualResponse.body as Person
        assertPerson(actualPerson)
    }

    @Test
    fun getListTest() {
        addTestPerson()
        val actualResponse = restTemplate.exchange(
            "http://localhost:$port/api/list",
            HttpMethod.GET,
            HttpEntity("{name: \"$NAME\"}", addCookiesToHeaders()),
            Array<Person>::class.java
        )
        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(1, actualResponse.body?.size)
        val actualPerson = actualResponse.body?.first() as Person
        assertPerson(actualPerson)
    }

    @Test
    fun viewTest() {
        addTestPerson()
        val resp =
            restTemplate.exchange(
                "http://localhost:$port/api/1/view",
                HttpMethod.GET,
                HttpEntity(null, addCookiesToHeaders()),
                Person::class.java
            )
        assertEquals(HttpStatus.OK, resp.statusCode)
        assertEquals(NAME, resp.body?.name)
    }

    @Test
    fun deleteTest() {
        addTestPerson()
        val resp =
            restTemplate.exchange(
                "http://localhost:$port/api/1/delete",
                HttpMethod.DELETE,
                HttpEntity(null, addCookiesToHeaders()),
                String::class.java
            )
        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun editCustomerTest() {
        addTestPerson()
        val newName = "$NAME New"
        val person = Person(0, newName, ADDRESS, PHONE)
        val resp = restTemplate.exchange(
            "http://localhost:$port/api/1/edit",
            HttpMethod.PUT,
            HttpEntity(person, addCookiesToHeaders()),
            String::class.java
        )
        assertEquals(HttpStatus.OK, resp.statusCode)

        val actualResponse =
            restTemplate.exchange(
                "http://localhost:$port/api/list",
                HttpMethod.GET,
                HttpEntity(null, addCookiesToHeaders()),
                Array<Person>::class.java
            )
        assertEquals(1, actualResponse.body?.size)
        val actualPerson = actualResponse.body?.first() as Person
        assertEquals(newName, actualPerson.name)
    }

    private fun assertPerson(person: Person) {
        assertEquals(NAME, person.name)
        assertEquals(ADDRESS, person.address)
        assertEquals(PHONE, person.phone)
    }

    private fun addTestPerson(): ResponseEntity<Person> {
        val person = Person(0, NAME, ADDRESS, PHONE)
        return restTemplate.postForEntity(
            "http://localhost:$port/api/add",
            HttpEntity(person, addCookiesToHeaders()),
            Person::class.java
        )
    }

    private fun addCookiesToHeaders(): HttpHeaders =
        HttpHeaders().also { it.add("Cookie", "auth=${LocalDateTime.now()}") }
}