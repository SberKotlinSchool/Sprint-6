package ru.sber

import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.sber.dto.Address
import ru.sber.repository.AddressBook

import java.util.*
@SpringBootTest
@AutoConfigureMockMvc
class MvcControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun list() {
        mockMvc.perform(get("/app/list"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("list"))
            .andExpect(content().string(containsString("Книга адресов")))
            .andExpect(content().string(containsString("Добавить")))
    }

    @Test
    fun postAdd(){

        val address = Address("Name1", "Email1")
        mockMvc.perform(post("/app/add").flashAttr("address", address ) )
            .andDo(print())
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))
            .andExpect(redirectedUrl("/app/list"))
    }

    @Test
    fun getAdd(){

        mockMvc.perform(get("/app/add").flashAttr("address", Address() ) )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("add"))
    }

    @Test
    fun getView(){

        val address = Address("Name1", "Email1")

       mockMvc.perform(
            post("/app/add")
                .flashAttr("address", address)
        )
        mockMvc
            .perform(get("/app/0/view"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("view"))
    }

    @Test
    fun postEdit(){

        val address = Address("Name1", "Email1")
        val addressChange = Address("Name1", "Email1")
        mockMvc.perform(post("/app/add").flashAttr("address", address ) )
        mockMvc
            .perform(post("/app/0/edit").flashAttr("address", addressChange ) )
            .andDo(print())
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))
    }
    @Test
    fun getDelete(){

        val address = Address("Name1", "Email1")
        mockMvc.perform(post("/app/add").flashAttr("address", address ) )
        mockMvc
            .perform(get("/app/0/delete" ) )
            .andDo(print())
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))
    }
}