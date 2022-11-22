package ru.sber.ufs.cc.kulinich.springmvc

import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import javax.servlet.http.Cookie

@AutoConfigureMockMvc
@SpringBootTest
class SpringMvcApplicationTests {


    @Autowired
    private lateinit var mockMvc: MockMvc

    private fun getAuthCookie(): Cookie =
        Cookie("auth", "${LocalDate.now()}")

    @BeforeEach
    fun addContacts() {
        mockMvc.perform(post("/app/add")
            .cookie(getAuthCookie())
            .param("name", "Roma")
            .param("phone", "89151371111"))
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))
    }


    @Test
    fun addContactstest() {
        mockMvc.perform(post("/app/add")
                .cookie(getAuthCookie())
                .param("name", "Kostya")
                .param("phone", "89151371112"))
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))

        mockMvc.perform(post("/app/add")
                .cookie(getAuthCookie())
                .param("name", "Gustav")
                .param("phone", "89151371112"))
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))
    }

    @Test
    fun `add contacts fail due to not logged in`() {
        mockMvc.perform(post("/app/add")
            .param("name", "Gustav")
            .param("phone", "89151371112"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/login"))
    }

    @Test
    fun goToListTest() {
        mockMvc.perform(get("/app/list")
                .cookie(getAuthCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
    }



    @Test
    fun viewTest() {
        mockMvc.perform(get("/app/1397711761/view")
                .cookie(getAuthCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("view"))
    }

    @Test
    fun deleteTest() {
        mockMvc.perform(get("/app/1/delete")
                .cookie(getAuthCookie()))
            .andDo(::print)
            .andExpect(status().is3xxRedirection)
    }

    @Test
    fun editTest() {
        //ok
        mockMvc.perform(
            post("/app/1/edit")
                .cookie(getAuthCookie())
                .param("name", "Leha")
                .param("phone", "89151221111111"))
            .andDo(::print)
            .andExpect(status().is3xxRedirection)
    }
}
