package ru.shadowsith.addressbook.controllers


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import ru.shadowsith.addressbook.dto.Record
import ru.shadowsith.addressbook.repositories.AddressBookRepository
import java.net.URI
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressBookApiControllerTests {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var addressBookRepository: AddressBookRepository

    @Test
    fun addRecordTest() {
        val path = "/api/address-book"
        val record = Record(name = "qwe", address = "address", phone = "123")
        val actualResponse = restTemplate.exchange(
           URI("http://localhost:${port}$path"),
            HttpMethod.POST,
            createHttpEntity(record),
            Record::class.java)
        val actualRecord = actualResponse.body
        assertNotNull(actualResponse)
        assertEquals(HttpStatus.CREATED, actualResponse.statusCode)
        assertEquals(record.copy(guid = actualRecord?.guid, createDataTime = actualRecord?.createDataTime!!), actualRecord)
    }

    @Test
    fun getRecordTest() {
        val path = "/api/address-book"
        val recordOne = Record(name = "qwe", address = "address", phone = "123")
        addressBookRepository.create(recordOne)
        val recordTwo = Record(name = "asd", address = "asd", phone = "42332")
        addressBookRepository.create(recordTwo)
        val records = addressBookRepository.readAll()

        val actualResponse = restTemplate.exchange(
            URI("http://localhost:${port}$path"),
            HttpMethod.GET,
            createHttpEntity(null),
            object : ParameterizedTypeReference<List<Record>>() {})
        val actualRecords = actualResponse.body!!
        assertNotNull(actualResponse)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(records, actualRecords)
        assertEquals(2, actualRecords.size)
    }


    @Test
    fun getRecordByNameTest() {
        val path = "/api/address-book?name=qwe11"
        val record = Record(name = "qwe11", address = "address", phone = "123")
        addressBookRepository.create(record)

        val records = addressBookRepository.findByName("qwe11")

        val actualResponse = restTemplate.exchange(
            URI("http://localhost:${port}$path"),
            HttpMethod.GET,
            createHttpEntity(null),
            object : ParameterizedTypeReference<List<Record>>() {})
        val actualRecords = actualResponse.body!!
        assertNotNull(actualResponse)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(records, actualRecords)
        assertEquals(1, actualRecords.size)
    }
    @Test
    fun getRecordByIdTest() {

        val record = Record(name = "qwe", address = "address", phone = "123")
        val guid = addressBookRepository.create(record).guid!!
        val path = "/api/address-book/$guid"
        val expectedRecord = addressBookRepository.read(guid)

        val actualResponse = restTemplate.exchange(
            URI("http://localhost:${port}$path"),
            HttpMethod.GET,
            createHttpEntity(null),
            Record::class.java)
        val actualRecord = actualResponse.body!!
        assertNotNull(actualResponse)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(expectedRecord, actualRecord)

    }

    @Test
    fun changeRecordTest() {
        var record = Record(name = "qwe", address = "address", phone = "123")
        record = addressBookRepository.create(record)
        val expectedRecord = record.copy(name = "rtet", phone = "321")
        val path = "/api/address-book/${record.guid}"

        val actualResponse = restTemplate.exchange(
            URI("http://localhost:${port}$path"),
            HttpMethod.PUT,
            createHttpEntity(expectedRecord),
            Record::class.java)
        val actualRecord = actualResponse.body!!
        assertNotNull(actualResponse)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(expectedRecord, actualRecord)
    }

    @Test
    fun deleteRecordTest() {
        var record = Record(name = "qwe", address = "address", phone = "123")
        record = addressBookRepository.create(record)

        val path = "/api/address-book/${record.guid}"

        val actualResponse = restTemplate.exchange(
            URI("http://localhost:${port}$path"),
            HttpMethod.DELETE,
            createHttpEntity(null),
            Record::class.java)
        val actualRecord = actualResponse.body!!

        val expectedResult = addressBookRepository.read(record.guid!!)
        assertNull(expectedResult)
        assertNotNull(actualResponse)
        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(record, actualRecord)
    }

    fun <T>createHttpEntity(body: T?) : HttpEntity<T> {
        val dataTime = LocalDateTime.now().minusMinutes(1L)
        val headers = HttpHeaders()
        headers.add(HttpHeaders.COOKIE,"auth=$dataTime")
        return HttpEntity<T>(body, headers)
    }

}
