package ru.sber.springmvc

import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.sber.springmvc.dto.Entry

@SpringBootTest
@AutoConfigureMockMvc
class AppControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val testEntry = Entry("name", "phoneNumber")

    @BeforeEach
    fun addEntry() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("name", testEntry.name)
                .param("phoneNumber", testEntry.phoneNumber)
        )
    }

    @Test
    fun addGet() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("add"))
    }

    @Test
    fun addPost() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("name", testEntry.name)
                .param("phoneNumber", testEntry.phoneNumber)
        )
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
        .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    @Test
    fun list() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("list"))
            .andCheckEntryPresence()
    }

    @Test
    fun view() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/${testEntry.name}/view"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("view"))
            .andCheckEntryPresence()
    }

    @Test
    fun editGet() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/${testEntry.name}/edit"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("edit"))
    }

    @Test
    fun editPost() {
        val newEntry = Entry("newName", "newPhoneName")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/${testEntry.name}/edit")
                .param("name", newEntry.name)
                .param("phoneNumber", newEntry.phoneNumber)
        )
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
        .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    @Test
    fun deleteGet() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/${testEntry.name}/delete"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("delete"))
    }

    @Test
    fun deletePost() {
        mockMvc.perform(MockMvcRequestBuilders.post("/app/${testEntry.name}/delete"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    private fun ResultActions.andCheckEntryPresence() =
        andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(testEntry.name)))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(testEntry.phoneNumber)))
}