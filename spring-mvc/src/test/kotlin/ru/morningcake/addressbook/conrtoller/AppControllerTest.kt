package ru.morningcake.addressbook.conrtoller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.morningcake.addressbook.BaseNotesTest
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AppControllerTest : BaseNotesTest() {

    @Test
    @DisplayName("GET /app/list")
    fun showBook() {
        val mvcResult = GET("/app/list", status().isOk)

        val html = getHtmlResponse(mvcResult)
        assertTrue(html.contains("<td>${note1.name}</td>"))
        assertTrue(html.contains("<td>${note1.phone}</td>"))
        assertTrue(html.contains("<td>${note1.address}</td>"))
        assertTrue(html.contains("<td>${note2.name}</td>"))
        assertTrue(html.contains("<td>${note2.phone}</td>"))
        assertTrue(html.contains("<td>${note2.address}</td>"))
    }

    @ParameterizedTest(name = "#{index} - filter {0}")
    @DisplayName("POST /app/filter")
    @ValueSource(strings = ["%2B7123", "me1", "ess1"]) //+ %2B
    fun showFilteredBook(filter : String) {
        // отфильтруем вторую запись
        val mvcResult = POST("/app/filter", "filter=$filter", status().isOk)

        val html = getHtmlResponse(mvcResult)
        assertTrue(html.contains("<td>${note1.name}</td>"))
        assertTrue(html.contains("<td>${note1.phone}</td>"))
        assertTrue(html.contains("<td>${note1.address}</td>"))
        // второй записи нет в ответе
        assertFalse(html.contains("<td>${note2.name}</td>"))
        assertFalse(html.contains("<td>${note2.phone}</td>"))
        assertFalse(html.contains("<td>${note2.address}</td>"))
    }

    @Test
    @DisplayName("GET /app/note/{id}")
    fun showNote() {
        val mvcResult = GET("/app/note/${note1.id}", status().isOk)

        val html = getHtmlResponse(mvcResult)
        assertTrue(html.contains("""<input type="text" name="name".+value="${note1.name}">""".toRegex()))
        assertTrue(html.contains("""<input type="tel" name="phone".+value="\${note1.phone}">""".toRegex()))
        assertTrue(html.contains("""<input type="text" name="address".+value="${note1.address}">""".toRegex()))
        assertTrue(html.contains("""<textarea name="comment".+>${note1.comment}</textarea>""".toRegex()))
    }

    @Test
    @DisplayName("POST /app/create")
    fun createNote() {
        val newNoteFormData = getDtoString()
        val mvcResult = POST("/app/create", newNoteFormData, status().isOk)

        val html = getHtmlResponse(mvcResult)
        assertTrue(html.contains("""<input type="text" name="name".+value="test">""".toRegex()))
        assertTrue(html.contains("""<input type="tel" name="phone".+value="\+123456789012">""".toRegex()))
        assertTrue(html.contains("""<input type="text" name="address".+value="samara">""".toRegex()))
        assertTrue(html.contains("""<textarea name="comment".+></textarea>""".toRegex()))

        assertEquals(3, noteRepository.count())
        val created = noteRepository.getAll().first { it.name == "test" }
        assertNotNull(created)
        assertEquals("test", created.name)
        assertEquals("+123456789012", created.phone)
        assertEquals("samara", created.address)
        assertEquals("", created.comment)
    }

    @Test
    @DisplayName("POST /app/note/{id}/update")
    fun updateNote() {
        val newNoteFormData = "${getDtoString()}update"
        val mvcResult = POST("/app/note/${note1.id}/update", newNoteFormData, status().isOk)

        val html = getHtmlResponse(mvcResult)
        assertTrue(html.contains("""<input type="text" name="name".+value="test">""".toRegex()))
        assertTrue(html.contains("""<input type="tel" name="phone".+value="\+123456789012">""".toRegex()))
        assertTrue(html.contains("""<input type="text" name="address".+value="samara">""".toRegex()))
        assertTrue(html.contains("""<textarea name="comment".+>update</textarea>""".toRegex()))

        assertEquals(2, noteRepository.count())
        val updated = noteRepository.getById(note1.id)
        assertNotNull(updated)
        assertEquals("test", updated!!.name)
        assertEquals("+123456789012", updated.phone)
        assertEquals("samara", updated.address)
        assertEquals("update", updated.comment)
    }

    @Test
    @DisplayName("GET /app/note/{id}/delete")
    fun deleteNote() {
        val mvcResult = GET("/app/note/${note1.id}/delete", status().isOk)

        val html = getHtmlResponse(mvcResult)
        // первой записи нет в ответе
        assertFalse(html.contains("<td>${note1.name}</td>"))
        assertFalse(html.contains("<td>${note1.phone}</td>"))
        assertFalse(html.contains("<td>${note1.address}</td>"))
        // вторая запись есть в ответе
        assertTrue(html.contains("<td>${note2.name}</td>"))
        assertTrue(html.contains("<td>${note2.phone}</td>"))
        assertTrue(html.contains("<td>${note2.address}</td>"))

        assertEquals(1, noteRepository.count())
        assertTrue(noteRepository.getAll().none { it.name == note1.name })
    }

    @ParameterizedTest(name = "#{index} -  {0} {1}")
    @DisplayName("Auth test")
    @MethodSource("appAuthTestArguments")
    fun apiAuthTest(method : HttpMethod, uri : String, content : Any?) {
        val mvcResult = when (method) {
            GET -> withoutCookieGET(uri, status().isFound) // 302 redirect
            POST -> withoutCookiePOST(uri, writeJson(content!!), status().isFound)
            else -> throw IllegalArgumentException("Неподдерживаемый метод для теста!")
        }
        assertEquals("/login", mvcResult.response.getHeader("Location"))
    }

    private fun appAuthTestArguments() : Stream<Arguments> {
        return Stream.of(
            Arguments.of(GET, "/app/list", null),
            Arguments.of(POST, "/app/filter", "filter=me1"),
            Arguments.of(GET, "/app/note/${note1.id}", null),
            Arguments.of(POST, "/app/create", getDtoString()),
            Arguments.of(POST, "/app/note/${note1.id}/update", getDtoString()),
            Arguments.of(GET, "/app/note/${note1.id}/delete", null)
        )
    }

    private fun getDtoString() : String {
        return "name=test&phone=%2B123456789012&address=samara&comment=" //+ %2B
    }
}