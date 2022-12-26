package ru.sber.addressbook.controller

import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import ru.sber.addressbook.model.Person

@SpringBootTest
@AutoConfigureMockMvc
internal class PersonControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    var person = Person(2, "Va", "CA")

    @Test
    fun getPersonList() {
        mockMvc.perform(get("/persons"))
            .andExpect(status().isOk)
            .andExpect(view().name("persons"))
    }

    @Test
    fun openAddForm() {
        mockMvc.perform(get("/persons/add"))
            .andExpect(status().isOk)
            .andExpect(view().name("add"))
    }

    @Test
    fun add() {
        mockMvc.perform(
            post("/persons/add")
                .param("id", person.id.toString())
                .param("name", person.name)
                .param("city", person.city)
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/persons"))
    }

    @Test
    fun getById() {
        mockMvc.perform(get("/persons/1/view"))
            .andExpect(status().isOk)
            .andExpect(view().name("view"))
    }


    @Test
    fun delete() {
        mockMvc.perform(get("/persons/0/delete"))
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/persons"))
    }

    @Test
    fun edit() {
        mockMvc.perform(get("/persons/1/edit"))
            .andExpect(status().isOk)
            .andExpect(view().name("edit"))
    }

    @Test
    fun update() {
        mockMvc.perform(
            post("/persons/1/edit")
                .param("id", person.id.toString())
                .param("name", person.name)
                .param("city", person.city)
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/persons"))
    }
}