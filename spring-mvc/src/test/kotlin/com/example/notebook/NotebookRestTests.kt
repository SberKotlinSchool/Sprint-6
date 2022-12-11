package com.example.notebook

import com.example.notebook.model.Note
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotebookRestTests {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    fun addHeader(): HttpHeaders = HttpHeaders().also { it.add("Cookie", "auth=${System.currentTimeMillis()}") }

    @Test
    fun addNoteTest() {
        val note = Note("new note")
        val headers = addHeader()
        val response = restTemplate.postForEntity(url("/api/notes/starikovamari/new"), HttpEntity(note, headers), List::class.java)

        val body = response.body as List<String>

        assertThat(body.contains("new note"))
    }

    @Test
    fun deleteNoteTest() {
        val headers = addHeader()
        val response = restTemplate.postForEntity(url("/api/notes/starikovamari/delete/0"), HttpEntity("", headers), List::class.java)

        val body = response.body as List<String>

        assertThat(!body.contains("first note"))
    }
}