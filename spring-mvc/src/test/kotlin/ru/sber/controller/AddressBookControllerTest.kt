package ru.sber.controller

import jakarta.servlet.http.Cookie
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(MockitoExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
class AddressBookControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val cookie = Cookie("auth", LocalDateTime.now().plusMinutes(10).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))


    @Test
    fun viewAppListTest() {
        mockMvc.perform(get("/app/list").cookie(cookie))
                .andExpect(status().isOk)
                .andExpect(view().name("allPersons"))
    }

    @Test
    fun showAddForm() {
        mockMvc.perform(get("/app/add")
                .param("newName", "Name")
                .param("address", "Address")
                .cookie(cookie))
                .andExpect(view().name("updatePerson"))

    }

    @Test
    fun `test deletePerson`() {
        mockMvc.perform(get("/app/{id}/delete", 0).cookie(cookie))
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("/app/list"))
    }

}