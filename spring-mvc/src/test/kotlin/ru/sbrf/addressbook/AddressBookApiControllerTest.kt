package ru.sbrf.addressbook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.test.annotation.DirtiesContext
import ru.sbrf.addressbook.core.Employee
import java.time.LocalDateTime
import java.util.*
import java.util.Collections.singletonList


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(PER_CLASS)
internal class AddressBookApiControllerTest {

    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(path: String): String {
        return "http://localhost:${port}/${path}"
    }

    @BeforeEach
    fun addHeaderInterceptor() {
        restTemplate.restTemplate.interceptors
            .add(ClientHttpRequestInterceptor
            { request, body, execution ->
                request.headers.put("Cookie", singletonList("auth=${LocalDateTime.now()}"))
                execution.execute(request, body)
            })
    }

    @Test
    fun addEmployee() {
        // when
        val resp = addTestEmployee()

        // then
        assertEquals(HttpStatus.OK, resp.statusCode)
        assertNotNull(resp.body)
    }


    @Test
    fun viewEmployee() {
        // given
        val resp = addTestEmployee()


        // when
        val responseEntity =
            restTemplate.getForEntity(
                url("api/${resp.body!!.id}/view"),
                Employee::class.java
            )

        // then
        assertNotNull(responseEntity.body!!)
    }
    
    @Test
    fun getListTest() {
        // given
        addTestEmployee()

        // when
        val responseEntity =
            restTemplate.getForEntity(
                url("api/list"),
                Array<Employee>::class.java
            )

        // then
        assertNotNull(responseEntity.body!![0])
    }


    @Test
    fun getListTestByQuery() {

        // given
        addSpecificTestEmployee("Александр", "Пушкин")
        addSpecificTestEmployee("Александр", "Сувовров")
        // when
        val responseEntity =
            restTemplate.getForEntity(
                url("api/list?lastname=Пушкин&firstname=Александр"),
                Array<Employee>::class.java
            )

        // then
        assertEquals(1, responseEntity.body!!.size)
        assertNotNull(responseEntity.body!![0])
    }


    @Test
    fun deleteEmployee() {

        // given
        val resp = addTestEmployee()

        // when
        val id = resp.body!!.id
        restTemplate.delete(url("api/${id}/delete"))

        // then
        val responseEntity =
            restTemplate.getForEntity(
                url("api/list?lastname=Пушкин&firstname=Александр"),
                Array<Employee>::class.java
            )
        assertEquals(0, responseEntity.body!!.size)
    }

    @Test
    fun editEmployee() {

        // given
        val resp = addTestEmployee()

        //
        val employee = resp.body
        val id = employee!!.id
        val employeeEdited = employee.copy(lastName = "Edit")
        val employeeUpdated =
            restTemplate.postForEntity(
                url("api/${id}/edit"),
                HttpEntity(employeeEdited),
                Employee::class.java
            )

        // when
        val responseEntity = restTemplate.getForEntity(url("api/${id}/edit"), Employee::class.java)

        // then
        assertEquals(employeeUpdated.body!!.id, responseEntity.body!!.id)
    }


    private fun addTestEmployee(): ResponseEntity<Employee> {
        val customer = Employee(0, "Имян", "Фамильевич", "Отчествов")
        return restTemplate.postForEntity(
            url("api/add"),
            HttpEntity(customer),
            Employee::class.java
        )
    }

    private fun addSpecificTestEmployee(name: String, lastName: String): ResponseEntity<Employee> {
        val customer = Employee(0, name, lastName, "Отчествов")
        return restTemplate.postForEntity(
            url("api/add"),
            HttpEntity(customer),
            Employee::class.java
        )
    }
}
