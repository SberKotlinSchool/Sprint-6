package ru.sber.mvc

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.sber.mvc.controllers.AddressBookController
import ru.sber.mvc.data.AddressBookRepository
import ru.sber.mvc.domain.Record
import ru.sber.mvc.servlets.COOKIE_NAME_AUTH
import java.time.LocalDateTime
import java.util.UUID
import javax.servlet.http.Cookie


@SpringBootTest
@AutoConfigureMockMvc
class AddressBookControllerTests {

    private companion object {
        const val DEFAULT_RECORD_ID = "0"
        val DEFAULT_RECORD_VALUE = Record(
            "Dima",
            "+79876543211",
            "Saint-Petersburg",
            "Gym buddy",
            id = DEFAULT_RECORD_ID,
        )
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val validCookie: Cookie
        get() = Cookie(COOKIE_NAME_AUTH, LocalDateTime.now().toString())

    init {
        mockkStatic(UUID::class.java.name)
        every { UUID.randomUUID().toString() } returns DEFAULT_RECORD_ID
    }

    @Test
    fun `test valid app add get request`() {
        mockMvc.perform(get("/app/add").cookie(validCookie))
            .andExpectAll(
                status().isOk,
                model().attribute("newRecord", Record.EMPTY),
                view().name("add")
            )
    }

    @Test
    fun `test valid app add post request`() {
        val request = post("/app/add")
            .cookie(validCookie)
            .param("name", "Alex")
            .param("phone", "12345")
            .param("address", "somewhere")

        mockMvc.perform(request).andExpectAll(
            status().is3xxRedirection,
            redirectedUrl("${DEFAULT_RECORD_ID}/view")
        )
    }

    @Test
    fun `test valid app list get request`() {
        mockMvc.perform(get("/app/list").cookie(validCookie))
            .andExpectAll(
                status().isOk,
                model().attributeExists("records"),
                view().name("list")
            )
    }

    @Test
    fun `test valid app view get request`() {
        mockMvc.perform(get("/app/${DEFAULT_RECORD_ID}/view").cookie(validCookie))
            .andExpectAll(
                status().isOk,
                model().attribute("record", DEFAULT_RECORD_VALUE),
                view().name("view")
            )
    }

    @Test
    fun `test valid app edit get request`() {
        mockMvc.perform(get("/app/${DEFAULT_RECORD_ID}/edit").cookie(validCookie))
            .andExpectAll(
                status().isOk,
                model().attribute("newRecord", DEFAULT_RECORD_VALUE),
                view().name("edit")
            )
    }

    @Test
    fun `test valid app edit post request`() {
        val request = post("/app/${DEFAULT_RECORD_ID}/edit").cookie(validCookie)
            .param("name", "Kolya")
            .param("phone", "12345")
            .param("address", "Somewhere")

        mockMvc.perform(request).andExpectAll(
            status().is3xxRedirection,
            redirectedUrl("/app/${DEFAULT_RECORD_ID}/view")
        )
    }

    @Test
    fun `test valid delete post request`() {
        val request = post("/app/${DEFAULT_RECORD_ID}/delete").cookie(validCookie)

        mockMvc.perform(request).andExpectAll(
            status().is3xxRedirection,
            redirectedUrl("/app/list")
        )
    }
}
