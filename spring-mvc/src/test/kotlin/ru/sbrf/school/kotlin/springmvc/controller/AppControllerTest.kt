package ru.sbrf.school.kotlin.springmvc.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.sbrf.school.kotlin.springmvc.entity.Person
import java.time.LocalDateTime
import javax.servlet.http.Cookie

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class AppControllerTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var mockMvc: MockMvc

    private fun getAuthCookie(): Cookie = Cookie("auth", "${LocalDateTime.now()}")

    @Test
    fun `add get Person`() {
        mockMvc.perform(
            get("/app/add")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("add"))
    }

    @Test
    fun `add Post Person`() {
        mockMvc.perform(
            post("/app/add")
                .cookie(getAuthCookie())
                .flashAttr("person", Person(name = "Uasya", phone = "88 888 888"))
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(
                view().name("redirect:/app/list")
            )
    }

    @Test
    fun `add person and redirect to login`() {
        mockMvc.perform(
            post("/app/add")
                .flashAttr("person", Person(name = "Uasya", phone = "88 888 888"))
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/login"))
    }

    @Test
    fun `open persons list`() {
        mockMvc.perform(
            get("/app/list")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
    }


    @Test
    fun `open person view`() {
        mockMvc.perform(
            get("/app/0/view")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("view"))
    }

    @Test
    fun `delete person`() {
        mockMvc.perform(
            get("/app/0/delete")
                .cookie(getAuthCookie())
        )
            .andExpect(status().is3xxRedirection)
    }

    @Test
    fun `edit post person`() {
        //ok
        mockMvc.perform(
            post("/app/1/edit")
                .cookie(getAuthCookie())
                .flashAttr("person", Person(name = "Uasya", phone = "88 888 888"))
        )
            .andExpect(status().is3xxRedirection)
    }
    @Test
    fun `edit get person`() {
        //ok
        mockMvc.perform(
            get("/app/1/edit")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("edit"))
    }
}