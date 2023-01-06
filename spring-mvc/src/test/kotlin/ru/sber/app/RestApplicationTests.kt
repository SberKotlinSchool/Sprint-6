package ru.sber.app

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApplicationTests {
    @LocalServerPort
    private var port: Int = 0
    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    private val formModel = FormModel("Алексей","Алексеев","Алексеевка")
    private val cookie = HttpHeaders().apply {
        add("Cookie", "auth=${LocalDateTime.now()}")
        Thread.sleep(1000)
    }

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    @Test
    fun testFormAuthentication() {
        val res = restTemplate.getForEntity(url("/login"), String::class.java)
        assertEquals(res.statusCode, HttpStatus.OK)
    }

    @Test
    fun testWarn() {
        val res = restTemplate.getForEntity(url("/warn"), String::class.java)
        assertEquals(res.statusCode, HttpStatus.OK)
    }

    @Test
    fun testApi() {
        val result = "JSON API. Для работы с адресной книгой воспользуйтесь запросами. " +
                "Добавить запись /add. " +
                "Просмотреть список /list. " +
                "Просмотреть запись(id) /id/view. " +
                "Редактировать запись /id/edit. " +
                "Удалить запись /id/delete. " +
                "Параметры: firstName - имя, lastName - фамилия, city - город проживания."
        val res = restTemplate.exchange(
            url("/api"),
            HttpMethod.GET,
            HttpEntity<TestRestTemplate>(cookie),
            String::class.java)
        assertThat(res.body).contains(result)
        assertEquals(res.statusCode, HttpStatus.OK)
    }

    @Test
    fun testAdd() {
        val res = restTemplate.exchange(
            url("/api/add"),
            HttpMethod.POST,
            HttpEntity(formModel,cookie),
            String::class.java)
        assertEquals(res.statusCode, HttpStatus.OK)
    }

    @Test
    fun testList() {
        val res = restTemplate.exchange(
            url("/api/list"),
            HttpMethod.GET,
            HttpEntity(formModel,cookie),
            Collection::class.java)
        assertEquals(res.statusCode, HttpStatus.OK)
        assertThat(res.body).isNotNull
    }

    @Test
    fun testView() {
        val res = restTemplate.exchange(
            url("/api/1/view"),
            HttpMethod.GET,
            HttpEntity(formModel,cookie),
            FormModel::class.java)
        assertEquals(res.statusCode, HttpStatus.OK)
        assertThat(res.body).isNotNull
    }

    @Test
    fun testEdit() {
        val res = restTemplate.exchange(
            url("/api/1/edit"),
            HttpMethod.PUT,
            HttpEntity(formModel,cookie),
            String::class.java)
        assertEquals(res.statusCode, HttpStatus.OK)
    }

    @Test
    fun testDelete() {
        val res = restTemplate.exchange(
            url("/api/1/delete"),
            HttpMethod.DELETE,
            HttpEntity(formModel,cookie),
            String::class.java)
        assertEquals(res.statusCode, HttpStatus.OK)
    }
}