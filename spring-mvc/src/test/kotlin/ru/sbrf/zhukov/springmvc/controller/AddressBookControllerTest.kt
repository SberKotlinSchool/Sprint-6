package ru.sbrf.zhukov.springmvc.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.sbrf.zhukov.springmvc.data.AddressEntry
import ru.sbrf.zhukov.springmvc.service.AddressBookService

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class AddressBookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var addressBookService: AddressBookService

    @InjectMocks
    private lateinit var controller: AddressBookController

    @Test
    fun listEntries() {
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
    }

    @Test
    fun viewEntry() {
        val entryId = 1L
        val mockEntry = AddressEntry(entryId, "John Doe", "123 Main St")

        `when`(addressBookService.getEntry(entryId)).thenReturn(mockEntry)

        mockMvc.perform(get("/app/{id}/view", entryId))
            .andExpect(status().is3xxRedirection)  // Ожидаем перенаправление
            .andExpect(redirectedUrl("/app/list"))
    }

    @Test
    fun showAddForm() {
    }


    @Test
    fun showEditForm() {
        val entryId = 1L
        val mockEntry = AddressEntry(entryId, "John Doe", "123 Main St")

        `when`(addressBookService.getEntry(entryId)).thenReturn(mockEntry)

        mockMvc.perform(get("/app/{id}/edit", entryId))
            .andExpect(status().is3xxRedirection)  // Ожидаем перенаправление
            .andExpect(redirectedUrl("/app/list"))
    }


    @Test
    fun deleteEntry() {
        val entryId = 1L

        mockMvc.perform(get("/app/{id}/delete", entryId))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }
}