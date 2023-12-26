package addressbook.controller

import addressbook.model.Person
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RestControllersTest {

    @LocalServerPort
    private var port: Int = 0

    private val restTemplate = TestRestTemplate()

    @Test
    fun `test testGetPersons`() {
        val baseUrl = "http://localhost:$port/api/app"

        val responseWithoutParam = restTemplate.exchange(
            "$baseUrl/list",
            HttpMethod.GET,
            HttpEntity<Nothing>(apiUser()),
            object : ParameterizedTypeReference<List<Person>>() {}
        )
        assertEquals(HttpStatus.OK, responseWithoutParam.statusCode)
        val personsWithoutParam: List<Person> = responseWithoutParam.body ?: emptyList()

        val person = personsWithoutParam[0]
        assertEquals("Kirill", person.firstName)
        assertEquals("Timofeev", person.lastName)
        assertEquals("test@gmai.com", person.email)
        assertEquals("892313232123", person.phoneNumber)


        val firstName = "Kirill"
        val responseWithParam = restTemplate.exchange(
            "$baseUrl/list?firstName=$firstName",
            HttpMethod.GET,
            HttpEntity<Nothing>(apiUser()),
            object : ParameterizedTypeReference<List<Person>>() {}
        )
        assertEquals(HttpStatus.OK, responseWithParam.statusCode)
        val personsWithParam: List<Person> = responseWithParam.body ?: emptyList()

        val personWithParam = personsWithParam[0]
        assertEquals("Kirill", personWithParam.firstName)
        assertEquals("Timofeev", personWithParam.lastName)
        assertEquals("test@gmai.com", personWithParam.email)
        assertEquals("892313232123", personWithParam.phoneNumber)
    }

    @Test
    fun `test testGetPersonsWithOutAuth`() {
        val baseUrl = "http://localhost:$port/api/app"

        val response = restTemplate.exchange(
            "$baseUrl/list",
            HttpMethod.GET,
            HttpEntity<Nothing>(HttpHeaders()),
            String::class.java
        )
        println(response)
        assertTrue { response.statusCode == HttpStatus.FORBIDDEN }
    }
    @Test
    fun `test testGetPersonsNotAuthWithUser`() {
        val baseUrl = "http://localhost:$port/api/app"

        val response = restTemplate.exchange(
            "$baseUrl/list",
            HttpMethod.GET,
            HttpEntity<Nothing>(user()),
            String::class.java
        )

        assertTrue { response.statusCode == HttpStatus.FORBIDDEN }
    }

    @Test
    fun `test testAddPerson`() {
        val baseUrl = "http://localhost:$port/api/app"
        val newPerson = Person(id = null, "Elena", "Rotova")

        val httpEntity = HttpEntity(newPerson, apiUser())

        val response: ResponseEntity<Int> = restTemplate.exchange(
            "$baseUrl/add",
            HttpMethod.POST,
            HttpEntity<Nothing>(apiUser()),
            Int::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body)
    }

    @Test
    fun `test AddPersonWithOutCookie`() {
        val baseUrl = "http://localhost:$port/api/app"
        val newPerson = Person(id = null, "Elena", "Rotova")

        val response = restTemplate.exchange(
            "$baseUrl/add",
            HttpMethod.POST,
            null,
            String::class.java
        )
        assertTrue { response.statusCode == HttpStatus.FORBIDDEN }
    }


    @Test
    fun `test viewEntry`() {
        val baseUrl = "http://localhost:$port/api/app"
        val existingPersonId = 1

        val responseEntity: ResponseEntity<Person> = restTemplate.exchange(
            "$baseUrl/$existingPersonId/view",
            HttpMethod.GET,
            HttpEntity<Nothing>(apiUser()),
            object : ParameterizedTypeReference<Person>() {}
        )

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        val person: Person? = responseEntity.body
        assertEquals("Kirill", person?.firstName)
        assertEquals("Timofeev", person?.lastName)
        assertEquals("test@gmai.com", person?.email)
        assertEquals("892313232123", person?.phoneNumber)
    }

    @Test
    fun `test viewEntryWithOutCookie`() {
        val baseUrl = "http://localhost:$port/api/app"
        val existingPersonId = 1

        val response = restTemplate.exchange(
            "$baseUrl/$existingPersonId/view",
            HttpMethod.POST,
            null,
            String::class.java
        )
        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)

    }

    @Test
    fun `test editEntry`() {
        val baseUrl = "http://localhost:$port/api/app"
        val existingPersonId = 0
        val updatedPerson = Person(null, "Test", "Test")

        val response = restTemplate.exchange(
            "$baseUrl/$existingPersonId/edit",
            HttpMethod.PUT,
            HttpEntity<Nothing>(apiUser()),
            String::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `test editEntryWithOutCookie`() {
        val baseUrl = "http://localhost:$port/api/app"
        val existingPersonId = 0
        val updatedPerson = Person(null, "Test", "Test")

        val response = restTemplate.exchange(
            "$baseUrl/$existingPersonId/edit",
            HttpMethod.PUT,
            null,
            String::class.java
        )

        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
    }

    @Test
    fun `test deleteEntry`() {
        val baseUrl = "http://localhost:$port/api/app"
        val existingPersonId = 0

        val response = restTemplate.exchange(
            "$baseUrl/$existingPersonId/delete",
            HttpMethod.DELETE,
            HttpEntity<Nothing>(adminUser()),
            String::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `test deleteEntryWithOutCookie`() {
        val baseUrl = "http://localhost:$port/api/app"
        val existingPersonId = 0

        val response = restTemplate.exchange(
            "$baseUrl/$existingPersonId/delete",
            HttpMethod.DELETE,
            null,
            String::class.java
        )
        assertTrue { response.statusCode == HttpStatus.FORBIDDEN }

    }

    private fun adminUser(): HttpHeaders = authHeader("admin", "admin")
    private fun apiUser(): HttpHeaders = authHeader("api", "api")
    private fun user(): HttpHeaders = authHeader("user", "user")


    private fun authHeader(username: String, password: String): HttpHeaders {
        val headers = HttpHeaders()
        headers.setBasicAuth(username, password)
        return headers
    }
}