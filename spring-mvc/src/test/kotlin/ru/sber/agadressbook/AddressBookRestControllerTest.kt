package ru.sber.agadressbook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.test.annotation.DirtiesContext
import org.springframework.web.client.RestTemplate
import ru.sber.agadressbook.models.Person


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressBookRestControllerTest {

    private val host: String = "localhost"
    @LocalServerPort
    private val port: Int = 0


    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val record = Person(555, "Аристарх", "111-11-11", "переулок Сивцев Вражек")

    private fun getUrl(endPoint: String, isSecured: Boolean): String {
        return if (isSecured) {
            "https://$host:$port/$endPoint"
        } else "http://$host:$port/$endPoint"
    }


    @Test()
    fun viewRecordTest() {

        val response = restTemplate.exchange(
            getUrl( "/addressbook/api/2/view", false),
            HttpMethod.GET,
            HttpEntity(record),
            Person::class.java
        )
        assertNotNull(response)
        assertEquals(response.body?.phoneNumber, "777-77-77")

    }

    @Test()
    fun getRecordListTest() {

        val response = restTemplate.exchange(
            getUrl("/addressbook/api/list", false),
            HttpMethod.GET,
            HttpEntity(record),
            Array<Person>::class.java
        )
        assertNotNull(response)
        assertEquals(response.body?.first()?.firstName, "Пиастр")
        assertEquals(2, response.body?.size)

    }

    @Test
    fun addRecordTest() {
        val response = restTemplate.postForEntity(
            getUrl("/addressbook/api/add", false),
            HttpEntity(record),
            Person::class.java
        )
        assertNotNull(response)
        assertEquals(response.body?.firstName, record.firstName)
    }

    @Test
    fun deleteRecordTest() {
        val response = restTemplate.exchange(
            getUrl( "/addressbook/api/2/delete", false),
            HttpMethod.DELETE,
            HttpEntity(record),
            String::class.java
        )
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun editRecordTest() {

        //По другому метод Patch запустить не получилось
        val restTemplate = RestTemplate(HttpComponentsClientHttpRequestFactory())

        val editedRecord = Person(1, "Пиастр второй", "333-33-33", "Редактируемая улица")

        val response = restTemplate.exchange(
            getUrl("/addressbook/api/2/edit", false),
            HttpMethod.PATCH,
            HttpEntity(editedRecord),
            Person::class.java
        )
        assertNotNull(response)
        assertEquals(response.body?.firstName, editedRecord.firstName)
    }
}