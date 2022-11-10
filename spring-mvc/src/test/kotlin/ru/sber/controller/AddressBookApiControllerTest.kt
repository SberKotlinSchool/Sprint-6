package ru.sber.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext
import ru.sber.model.Customer
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AddressBookApiControllerTest {
    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val fio = "Testov Test Testovich"
    private val address = "Address"
    private val phone = "1234567890"
    private val email = "testov@mail.ru"

    private fun url(path: String): String {
        return "http://localhost:${port}/${path}"
    }

    @Test
    fun addCustomerTestSuccessful() {
        val actualResponse = addTestCustomer()
        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        val actualCustomer = actualResponse.body as Customer
        assertCustomer(actualCustomer)
    }

    @Test
    fun getListTest() {
        addTestCustomer()
        val actualResponse = restTemplate.exchange(
            url("api/list"),
            HttpMethod.GET,
            HttpEntity("{fio: \"${fio}\"}", addCookiesToHeaders()),
            Array<Customer>::class.java
        )
        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(1, actualResponse.body?.size)
        val actualCustomer = actualResponse.body?.first() as Customer
        assertCustomer(actualCustomer)
    }


    private fun assertCustomer(actualCustomer: Customer) {
        assertEquals(fio, actualCustomer.fio)
        assertEquals(address, actualCustomer.address)
        assertEquals(phone, actualCustomer.phone)
        assertEquals(email, actualCustomer.email)
    }

    @Test
    fun viewTest() {
        addTestCustomer()
        val resp =
            restTemplate.exchange(
                url("api/1/view"),
                HttpMethod.GET,
                HttpEntity(null, addCookiesToHeaders()),
                Customer::class.java
            )
        assertEquals(HttpStatus.OK, resp.statusCode)
        assertEquals(fio, resp.body?.fio)
    }

    @Test
    fun deleteTest() {
        addTestCustomer()
        val resp =
            restTemplate.exchange(
                url("api/1/delete"),
                HttpMethod.DELETE,
                HttpEntity(null, addCookiesToHeaders()),
                String::class.java
            )
        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun editCustomerTest() {
        addTestCustomer()
        val newFio = fio + "Edit"
        val customer = Customer(1, newFio, address, email)
        val resp = restTemplate.exchange(
            url("api/1/edit"),
            HttpMethod.PUT,
            HttpEntity(customer, addCookiesToHeaders()),
            String::class.java
        )
        assertEquals(HttpStatus.OK, resp.statusCode)

        val actualResponse =
            restTemplate.exchange(
                url("api/list"),
                HttpMethod.GET,
                HttpEntity(null, addCookiesToHeaders()),
                Array<Customer>::class.java
            )
        assertEquals(1, actualResponse.body?.size)
        val actualCustomer = actualResponse.body?.first() as Customer
        assertEquals(newFio, actualCustomer.fio)
    }

    private fun addTestCustomer(): ResponseEntity<Customer> {
        val customer = Customer(1, fio, address, phone, email)
        return restTemplate.postForEntity(
            url("api/add"),
            HttpEntity(customer, addCookiesToHeaders()),
            Customer::class.java
        )
    }

    private fun addCookiesToHeaders(): HttpHeaders =
        HttpHeaders().also { it.add("Cookie", "auth=${LocalDateTime.now()}") }

}