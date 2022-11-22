package ru.sber.ufs.cc.kulinich.springmvc


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.sber.ufs.cc.kulinich.springmvc.models.AddressBookModel
import java.time.LocalDate
import javax.servlet.http.Cookie

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringRESTApplicationTests {

    @LocalServerPort
    private var port: Int = 0

    private fun getAuthCookie(): Cookie =
        Cookie("auth", "${LocalDate.now()}")


    @Autowired
    private lateinit var restTemplate: TestRestTemplate


    private fun url(path: String): String {
        return "http://localhost:${port}/${path}"
    }

    @BeforeEach
    private fun addTest(){
        //given
        restTemplate.postForEntity(
            url("/api/add"),
            HttpEntity(
                AddressBookModel(name = "Roman", phone = "89151112233"),
                HttpHeaders().apply { this.add("Cookie", "auth=${LocalDate.now()}") }
            ),
            AddressBookModel::class.java)
            .apply {
                assertNotNull(this)
                assertNotNull(this.body)
                assertEquals(HttpStatus.OK, this.statusCode)
            }
    }


    @Test
    fun getListTest() {
        //when
        val actualResponse = restTemplate.exchange(
            url("/api/list"),
            HttpMethod.GET,
            HttpEntity(
                null,
                HttpHeaders()
                    .apply { this.add("Cookie", "auth=${LocalDate.now()}") }),
            Array<AddressBookModel>::class.java
        )

        //then
        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(actualResponse.body!!.first().name, "Roman")
        assertEquals(actualResponse.body!!.first().phone, "89151112233")
    }


    @Test
    fun viewTest() {
        //when
        val actualResponse =
            restTemplate.exchange(
                url("/api/${"89151112233".hashCode()}/view"),
                HttpMethod.GET,
                HttpEntity(null,
                    HttpHeaders()
                        .apply { this.add("Cookie", "auth=${LocalDate.now()}") }),
                AddressBookModel::class.java
            )

        //then
        assertNotNull(actualResponse)
        assertNotNull(actualResponse.body)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals("Roman", actualResponse.body!!.name)
    }





    @Test
    fun editTest() {
        // when
        val editResponse = restTemplate.exchange(
            url("api/${"89151112233".hashCode()}/edit"),
            HttpMethod.POST,
            HttpEntity(
                AddressBookModel(name = "Gustav", phone = "89151112233"),
                HttpHeaders().apply { this.add("Cookie", "auth=${LocalDate.now()}") }),
            String::class.java
        )

        // then
        val editChechResponse =
            restTemplate.exchange(
                url("/api/list"),
                HttpMethod.GET,
                HttpEntity(null,
                    HttpHeaders().apply { this.add("Cookie", "auth=${LocalDate.now()}") }),
                Array<AddressBookModel>::class.java
            )
        assertEquals(HttpStatus.OK, editChechResponse.statusCode)
        assertNotNull(editChechResponse)
        assertNotNull(editChechResponse.body)
        assertEquals(editChechResponse.body!!.first().name, "Gustav")
    }

    @Test
    fun deleteTest() {
        // when
        val deleteResponse = restTemplate.exchange(
            url("/api/${"89151112233".hashCode()}/delete"),
            HttpMethod.POST,
            HttpEntity(
                null,
                HttpHeaders().apply { this.add("Cookie", "auth=${LocalDate.now()}") }),
            String::class.java
        )

        // then
        assertEquals(HttpStatus.OK, deleteResponse.statusCode)
        val editChechResponse =
            restTemplate.exchange(
                url("/api/list"),
                HttpMethod.GET,
                HttpEntity(null,
                    HttpHeaders().apply { this.add("Cookie", "auth=${LocalDate.now()}") }),
                Array<AddressBookModel>::class.java
            )
        assertEquals(HttpStatus.OK, editChechResponse.statusCode)
        assertNotNull(editChechResponse)
        assertNotNull(editChechResponse.body)
        assert(editChechResponse.body!!.isEmpty())
    }
}
