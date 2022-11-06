package ru.company

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
class AddressBookMVCController {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("fio", "Ivan")
                .param("address", "Moon")
                .param("phone", "123456")
                .param("email", "moon@example.ru")
        )
    }

    @Test
    fun addClientFormTest() {

        mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("add"))

    }

    @Test
    fun addClientTest() {
        //ok
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("fio", "Inna")
                .param("address", "Address")
                .param("phone", "123456")
                .param("email", "email@example.ru")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(302))

        //client not valid
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("address", "Address")
                .param("phone", "123456")
                .param("email", "email@example.ru")
        )
            .andExpect(MockMvcResultMatchers.view().name("add"))
    }

    @Test
    fun getListWithoutParamTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("list"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Ivan")))
    }

    @Test
    fun getListWithParamTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/app/list")
                .param("address", "Moon")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("list"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Ivan")))
    }

    @Test
    fun viewTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/1/view"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("view"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Ivan")))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Moon")))
    }

    @Test
    fun deleteTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/1/delete"))
            .andExpect(MockMvcResultMatchers.status().`is`(302))
    }

    @Test
    fun editTest() {
        //ok
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/1/edit")
                .param("fio", "Inna")
                .param("address", "Address")
                .param("phone", "123456")
                .param("email", "email@example.ru")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(302))

        //client not valid
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/1/edit")
                .param("address", "Address")
                .param("phone", "123456")
                .param("email", "")
        )
            .andExpect(MockMvcResultMatchers.view().name("edit"))
    }


}
