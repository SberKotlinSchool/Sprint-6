package ru.sber.kotlinmvc.controllers

import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@AutoConfigureMockMvc
@SpringBootTest
internal class MvcControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/addressBook/add")
                .param("name", "Иванов Иван Иванович")
                .param("address", "Россия")
                .param("phone", "123456")
                .param("email", "ivanov@example.ru")
        )
    }

    @Test
    fun addClientFormTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/addressBook/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("add"))
    }

    @Test
    fun addClientTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/addressBook/add")
                .param("name", "Ivan")
                .param("address", "Omsk")
                .param("phone", "123456")
                .param("email", "ivan2@example.ru")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(302))
    }

    @Test
    fun getListTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/addressBook/list"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("list"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Иванов Иван Иванович")))
    }

    @Test
    fun getListWithParamTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/addressBook/list")
                .param("address", "Россия")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("list"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Иванов Иван Иванович")))
    }

    @Test
    fun viewTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/addressBook/1/view"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("view"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Иванов Иван Иванович")))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Россия")))
    }

    @Test
    fun editTest() {
        //ok
        mockMvc.perform(
            MockMvcRequestBuilders.post("/addressBook/1/edit")
                .param("name", "Ivan")
                .param("address", "Address")
                .param("phone", "123456")
                .param("email", "email@example.ru")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(302))
    }

    @Test
    fun deleteTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/addressBook/1/delete"))
            .andExpect(MockMvcResultMatchers.status().`is`(302))
    }


}

