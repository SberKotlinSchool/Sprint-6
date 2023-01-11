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
import org.springframework.http.HttpMethod.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.morningcake.addressbook.BaseNotesTest
import ru.morningcake.addressbook.dto.NoteDto
import ru.morningcake.addressbook.entity.Note
import ru.morningcake.addressbook.exception.EntityNotFoundException
import ru.morningcake.addressbook.exception.ValidationException
import java.util.*
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ApiControllerTest : BaseNotesTest() {

    private val baseUri = "/api/note"

    @Test
    @DisplayName("GET /api/note/list")
    fun getBook() {
        val mvcResult = apiGET("$baseUri/list", status().isOk)

        val notes = readDtoFromJson<List<Note>>(getJsonResponse(mvcResult))
        assertEquals(2, notes.size)
        listOf(note1, note2).forEach { expected ->
            val actual = notes.first { note -> note.id == expected.id }
            assertEquals(expected.name, actual.name)
            assertEquals(expected.phone, actual.phone)
            assertEquals(expected.address, actual.address)
            assertEquals(expected.comment, actual.comment)
        }
    }

    @ParameterizedTest(name = "#{index} - filter {0}")
    @DisplayName("GET /api/note/list with filter")
    @ValueSource(strings = ["+7123", "me1", "ess1"]) //+ %2B
    fun getBookWithFilter(filter : String) {
        val mvcResult = apiGET("$baseUri/list?filter=$filter", status().isOk)

        val notes = readDtoFromJson<List<Note>>(getJsonResponse(mvcResult))
        assertEquals(1, notes.size)
        val actual = notes.first()
        assertEquals(note1.id, actual.id)
        assertEquals(note1.name, actual.name)
        assertEquals(note1.phone, actual.phone)
        assertEquals(note1.address, actual.address)
        assertEquals(note1.comment, actual.comment)
    }

    @Test
    @DisplayName("GET /api/note/{id}")
    fun getNote() {
        val mvcResult = apiGET("$baseUri/${note1.id}", status().isOk)

        val actual = readDtoFromJson<Note>(getJsonResponse(mvcResult))
        assertNotNull(actual)
        assertEquals(note1.id, actual.id)
        assertEquals(note1.name, actual.name)
        assertEquals(note1.phone, actual.phone)
        assertEquals(note1.address, actual.address)
        assertEquals(note1.comment, actual.comment)
    }

    @Test
    @DisplayName("GET /api/note/{id} - incorrect id")
    fun getNote_incorrectId() {
        val randomUUID = UUID.randomUUID()
        val mvcResult = apiGET("$baseUri/$randomUUID", status().isNotFound)

        val ex = getExceptionResponse(mvcResult)
        assertNotNull(ex)
        assertTrue(ex is EntityNotFoundException)
        assertEquals("Запись $randomUUID не найдена!", ex!!.message)
    }

    @Test
    @DisplayName("POST /api/note")
    fun createNote() {
        val noteDto = getDto()
        val mvcResult = apiPOST(baseUri, writeJson(noteDto), status().isOk)

        val actual = readDtoFromJson<Note>(getJsonResponse(mvcResult))
        assertNotNull(actual)
        assertNotNull(actual.id)
        assertEquals(noteDto.name, actual.name)
        assertEquals(noteDto.phone, actual.phone)
        assertEquals(noteDto.address, actual.address)
        assertEquals(noteDto.comment, actual.comment)

        assertEquals(3, noteRepository.count())
        val created = noteRepository.getById(actual.id)
        assertEquals(noteDto.name, created!!.name)
        assertEquals(noteDto.phone, created.phone)
        assertEquals(noteDto.address, created.address)
        assertEquals(noteDto.comment, created.comment)
    }

    @Test
    @DisplayName("POST /api/note - validation test")
    fun createNote_validationTest() {
        val invalidDto = NoteDto(name = "", phone = "+12345", address = "", comment = "")
        val mvcResult = apiPOST(baseUri, writeJson(invalidDto), status().isBadRequest)

        val ex = getExceptionResponse(mvcResult)
        assertNotNull(ex)
        assertTrue(ex is ValidationException)
        assertTrue(ex!!.message!!.contains("name - не должно быть пустым!"))
        assertTrue(ex.message!!.contains("phone - не соответствует формату: от 11 до 15 цифр без пробелов, тире, скобок и пр., м.б. плюс в начале"))
        assertTrue(ex.message!!.contains("address - не должен быть пустым!"))
    }


    @Test
    @DisplayName("PUT /api/note/{id}")
    fun updateNote() {
        val noteDto = getDto()
        val mvcResult = apiPUT("$baseUri/${note1.id}", writeJson(noteDto), status().isOk)

        val actual = readDtoFromJson<Note>(getJsonResponse(mvcResult))
        assertNotNull(actual)
        assertNotNull(actual.id)
        assertEquals(noteDto.name, actual.name)
        assertEquals(noteDto.phone, actual.phone)
        assertEquals(noteDto.address, actual.address)
        assertEquals(noteDto.comment, actual.comment)

        assertEquals(2, noteRepository.count())
        val created = noteRepository.getById(actual.id)
        assertEquals(noteDto.name, created!!.name)
        assertEquals(noteDto.phone, created.phone)
        assertEquals(noteDto.address, created.address)
        assertEquals(noteDto.comment, created.comment)
    }

    @Test
    @DisplayName("PUT /api/note/{id} - incorrect id")
    fun updateNote_incorrectId() {
        val randomUUID = UUID.randomUUID()
        val noteDto = getDto()
        val mvcResult = apiPUT("$baseUri/$randomUUID", writeJson(noteDto), status().isNotFound)

        val ex = getExceptionResponse(mvcResult)
        assertNotNull(ex)
        assertTrue(ex is EntityNotFoundException)
        assertEquals("Запись $randomUUID не найдена!", ex!!.message)
    }

    @Test
    @DisplayName("PUT /api/note/{id} - validation test")
    fun updateNote_validationTest() {
        val invalidDto = NoteDto(name = "test", phone = "", address = "samara", comment = "")
        val mvcResult = apiPUT("$baseUri/${note1.id}", writeJson(invalidDto), status().isBadRequest)

        val ex = getExceptionResponse(mvcResult)
        assertNotNull(ex)
        assertTrue(ex is ValidationException)
        assertTrue(ex!!.message!!.contains("phone - не должен быть пустым!"))
    }

    @Test
    @DisplayName("DELETE /api/note/{id}")
    fun deleteNote() {
        apiDELETE("$baseUri/${note1.id}", status().isOk)

        assertEquals(1, noteRepository.count())
        assertNull(noteRepository.getById(note1.id))
    }

    @Test
    @DisplayName("DELETE /api/note/{id} - incorrect id")
    fun deleteNote_incorrectId() {
        val randomUUID = UUID.randomUUID()
        val mvcResult = apiDELETE("$baseUri/$randomUUID", status().isNotFound)

        val ex = getExceptionResponse(mvcResult)
        assertNotNull(ex)
        assertTrue(ex is EntityNotFoundException)
        assertEquals("Запись $randomUUID не найдена!", ex!!.message)
    }

    @ParameterizedTest(name = "#{index} -  {0} {1}")
    @DisplayName("Auth test")
    @MethodSource("apiAuthTestArguments")
    fun apiAuthTest(method : HttpMethod, uri : String, content : Any?) {
        when(method) {
            GET -> withoutCookieApiGET(uri, status().isForbidden)
            POST -> withoutCookieApiPOST(uri, writeJson(content!!), status().isForbidden)
            PUT -> withoutCookieApiPUT(uri, writeJson(content!!), status().isForbidden)
            DELETE -> withoutCookieApiDELETE(uri, status().isForbidden)
            else -> throw IllegalArgumentException("Неподдерживаемый метод для теста!")
        }
    }

    private fun apiAuthTestArguments() : Stream<Arguments> {
        return Stream.of(
            Arguments.of(GET, "$baseUri/list", null),
            Arguments.of(GET, "$baseUri/${note1.id}", null),
            Arguments.of(POST, baseUri, getDto()),
            Arguments.of(PUT, "$baseUri/${note1.id}", getDto()),
            Arguments.of(DELETE, "$baseUri/${note1.id}", null)
            )
    }

    private fun getDto() : NoteDto {
        return NoteDto(name = "test", phone = "+123456789012", address = "samara", comment = "")
    }
}