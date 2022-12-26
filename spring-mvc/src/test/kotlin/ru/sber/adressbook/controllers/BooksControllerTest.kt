package ru.sber.adressbook.controllers

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.util.NestedServletException

@SpringBootTest
@AutoConfigureMockMvc
class BooksControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext


    @BeforeEach
    fun init() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = ["USER"])
    fun list() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("allContact"))
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = ["USER"])
    fun addBook() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("addBook"))
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = ["ADMIN"])
    fun deleteBook() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("firstName", "Иван")
                .param("lastName", "Иванов")
                .param("email", "ivan666@mail.ru")
                .param("phone", "79876543211")
                .param("address", "РФ, Москва, ул. Пушкина")
        )

        mockMvc.perform(MockMvcRequestBuilders.get("/app/0/view"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("contactBook"))

        mockMvc.perform(MockMvcRequestBuilders.get("/app/0/delete"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))

        assertThrows<NestedServletException> { mockMvc.perform(MockMvcRequestBuilders.get("/app/0/view")) }
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = ["USER"])
    fun getBook() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("firstName", "Иван")
                .param("lastName", "Иванов")
                .param("email", "ivan666@mail.ru")
                .param("phone", "79876543211")
                .param("address", "РФ, Москва, ул. Пушкина")
        )

        mockMvc.perform(MockMvcRequestBuilders.get("/app/0/view"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("contactBook"))
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = ["USER"])
    fun editBook() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("firstName", "Иван")
                .param("lastName", "Иванов")
                .param("email", "ivan666@mail.ru")
                .param("phone", "79876543211")
                .param("address", "РФ, Москва, ул. Пушкина")
        )

        mockMvc.perform(MockMvcRequestBuilders.get("/app/0/edit"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("editBook"))

    }
}