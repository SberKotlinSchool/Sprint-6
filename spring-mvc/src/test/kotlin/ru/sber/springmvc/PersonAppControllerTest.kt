package ru.sber.springmvc

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonAppControllerTest {
    companion object {
        private const val NAME = "Иванов Иван"
        private const val ADDRESS = "Москва"
        private const val PHONE = "1234567890"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun addPersonGetTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("add"))
    }

    @Test
    fun addPersonPostTeest() {
        addTestPerson()
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))

    }

    @Test
    fun listPersonsTest() {
        addTestPerson()
        checkListRequestPerform(MockMvcRequestBuilders.get("/app/list"))
        checkListRequestPerform(MockMvcRequestBuilders.get("/app/list").param("name", NAME))
    }

    @Test
    fun deletePersonTest() {
        addTestPerson()
        mockMvc.perform(MockMvcRequestBuilders.get("/app/1/delete"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    @Test
    fun editPersonTest() {
        addTestPerson()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/1/edit")
                .param("name", "$NAME Edit")
                .param("address", ADDRESS)
                .param("phone", PHONE)
        )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/app/list")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("$NAME Edit")))
    }

    private fun addTestPerson(): ResultActions {
        return mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("name", NAME)
                .param("address", ADDRESS)
                .param("phone", PHONE)
        )
    }
    private fun checkListRequestPerform(requestBuilder: RequestBuilder) {
        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("list"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(NAME)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(ADDRESS)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(PHONE)))
    }
}