package ru.sber.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.servlet.view.RedirectView
import ru.sber.dto.Address
import java.time.LocalDateTime
import javax.servlet.http.Cookie

@SpringBootTest
@AutoConfigureMockMvc
class MvcControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    private val address = Address("n", "a", "p")

    val cookie = Cookie("auth", LocalDateTime.now().toString())

    @Test
    fun addAddress() {
        mockMvc
            .perform(
                post("/app/add")
                    .cookie(cookie)
                    .flashAttr("address", address)
            )
            .andExpect(status().is3xxRedirection)
            .andExpect { view() == RedirectView("list") }
    }

    @Test
    fun listAddress() {
        mockMvc
            .perform(get("/app/list").cookie(cookie))
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("list"))
    }

    @Test
    fun viewTest() {
        mockMvc.perform(
            post("/app/add")
                .cookie(cookie)
                .flashAttr("address", address)
        )
        mockMvc
            .perform(get("/app/0/view").cookie(cookie))
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("view"))
    }

    @Test
    fun edit() {
        mockMvc
            .perform(
                post("/app/0/edit")
                    .cookie(cookie)
                    .flashAttr("address", Address())
            )
            .andExpect(status().is3xxRedirection)
            .andExpect { view() == RedirectView("../list") }
    }

    @Test
    fun delete() {
        mockMvc
            .perform(post("/app/0/delete").cookie(cookie))
            .andExpect { view() == RedirectView("../list") }
    }
}