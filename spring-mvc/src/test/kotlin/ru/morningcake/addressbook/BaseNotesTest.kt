package ru.morningcake.addressbook

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import ru.morningcake.addressbook.dao.NoteRepository
import ru.morningcake.addressbook.dto.NoteDto
import ru.morningcake.addressbook.entity.Note
import java.nio.charset.StandardCharsets
import javax.servlet.http.Cookie


abstract class BaseNotesTest : BaseMvcTest() {

    @Autowired
    protected lateinit var noteRepository : NoteRepository

    protected lateinit var note1 : Note
    protected lateinit var note2 : Note
    protected final val authCookie : Cookie = Cookie("auth", "1600000")
    protected final var nameCookie : Cookie = Cookie("userName", "TestUser")

    init {
        authCookie.path = "/"
        nameCookie.path = "/"
    }

    @BeforeEach
    protected fun init() {
        note1 = noteRepository.create(
            NoteDto(name = "Name1", phone = "+71234567890", address = "Address1", comment = "Comment1")
        )
        note2 = noteRepository.create(
            NoteDto(name = "Name2", phone = "81234567890", address = "Address2", comment = "")
        )
    }

    @AfterEach
    protected fun clear() {
        noteRepository.clear()
    }

    protected fun getHtmlResponse(mvcResult : MvcResult) : String {
        return mvcResult.response.contentAsString.replace(System.lineSeparator(), "")
    }

    protected fun getJsonResponse(mvcResult : MvcResult) : String {
        return mvcResult.response.contentAsString
    }

    protected fun getExceptionResponse(mvcResult : MvcResult) : java.lang.Exception? {
        return mvcResult.resolvedException
    }

    // region REQUESTS WITH COOKIES

    protected fun GET(uri : String, status : ResultMatcher) : MvcResult {
        return mockMvc.perform(
            get(uri)
                .characterEncoding(StandardCharsets.UTF_8)
                .cookie(authCookie, nameCookie)
        ).andExpect(status).andReturn()
    }

    protected fun apiGET(uri : String, status : ResultMatcher) : MvcResult {
        return GET(uri, status)
    }

    protected fun POST(uri : String, content : String, status : ResultMatcher) : MvcResult {
        return POST(uri, content, status, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    }

    protected fun apiPOST(uri : String, content : String, status : ResultMatcher) : MvcResult {
        return POST(uri, content, status, MediaType.APPLICATION_JSON_VALUE)
    }

    private fun POST(uri : String, content : String, status : ResultMatcher, mediaType: String) : MvcResult {
        return mockMvc.perform(
            post(uri)
                .characterEncoding(StandardCharsets.UTF_8)
                .cookie(authCookie, nameCookie)
                .contentType(mediaType)
                .content(content)
        ).andExpect(status).andReturn()
    }

    protected fun apiPUT(uri : String, content : String, status : ResultMatcher) : MvcResult {
        return mockMvc.perform(
            put(uri)
                .characterEncoding(StandardCharsets.UTF_8)
                .cookie(authCookie, nameCookie)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
        ).andExpect(status).andReturn()
    }

    protected fun apiDELETE(uri : String, status : ResultMatcher) : MvcResult {
        return mockMvc.perform(
            delete(uri)
                .characterEncoding(StandardCharsets.UTF_8)
                .cookie(authCookie, nameCookie)
        ).andExpect(status).andReturn()
    }

    // endregion

    // region REQUESTS WITHOUT COOKIES

    protected fun withoutCookieGET(uri : String, status : ResultMatcher) : MvcResult {
        return mockMvc.perform(
            get(uri)
                .characterEncoding(StandardCharsets.UTF_8)
        ).andExpect(status).andReturn()
    }

    protected fun withoutCookieApiGET(uri : String, status : ResultMatcher) : MvcResult {
        return withoutCookieGET(uri, status)
    }

    protected fun withoutCookiePOST(uri : String, content : String, status : ResultMatcher) : MvcResult {
        return withoutCookiePOST(uri, content, status, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    }

    protected fun withoutCookieApiPOST(uri : String, content : String, status : ResultMatcher) : MvcResult {
        return withoutCookiePOST(uri, content, status, MediaType.APPLICATION_JSON_VALUE)
    }

    private fun withoutCookiePOST(uri : String, content : String, status : ResultMatcher, mediaType: String) : MvcResult {
        return mockMvc.perform(
            post(uri)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(mediaType)
                .content(content)
        ).andExpect(status).andReturn()
    }

    protected fun withoutCookieApiPUT(uri : String, content : String, status : ResultMatcher) : MvcResult {
        return mockMvc.perform(
            put(uri)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
        ).andExpect(status).andReturn()
    }

    protected fun withoutCookieApiDELETE(uri : String, status : ResultMatcher) : MvcResult {
        return mockMvc.perform(
            delete(uri)
                .characterEncoding(StandardCharsets.UTF_8)
        ).andExpect(status).andReturn()
    }

    // endregion
}