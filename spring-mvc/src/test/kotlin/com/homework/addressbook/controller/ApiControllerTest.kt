package com.homework.addressbook.controller

import com.homework.addressbook.dto.Record
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ApiControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    private val cookie = HttpHeaders().apply {
        add("Cookie", "auth=${LocalDateTime.now()}")
        Thread.sleep(1000)
    }

    @Test
    fun addRecord() {
        val response = restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            HttpEntity(Record(),cookie),
            String::class.java)
        assertEquals(response.statusCode, HttpStatus.OK)
    }

    @Test
    fun getRecords() {
        val response = restTemplate.exchange(
            url("/api/list"),
            HttpMethod.GET,
            HttpEntity("",cookie),
            String::class.java)
        assertEquals(response.statusCode, HttpStatus.OK)
        response.body?.let { assertTrue(it.contains("Хан")) }
    }

    @Test
    fun getCurrentRecord() {
        val response = restTemplate.exchange(
            url("/api/1/view"),
            HttpMethod.GET,
            HttpEntity("",cookie),
            String::class.java)
        assertEquals(response.statusCode, HttpStatus.OK)
        response.body?.let { assertTrue(it.contains("Падме")) }
    }

    @Test
    fun editRecord() {
        val record = Record("TEST","TEST","TEST","TEST");
        val response = restTemplate.exchange(
            url("/api/1/edit"),
            HttpMethod.POST,
            HttpEntity(record,cookie),
            String::class.java)
        assertEquals(response.statusCode, HttpStatus.OK)
    }

    @Test
    fun deleteRecord() {
        val response = restTemplate.exchange(
            url("/api/2/delete"),
            HttpMethod.DELETE,
            HttpEntity(Record(),cookie),
            String::class.java)
        assertEquals(response.statusCode, HttpStatus.OK)
    }
}