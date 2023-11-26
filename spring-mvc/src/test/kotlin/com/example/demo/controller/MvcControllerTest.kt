package com.example.demo.controller

import com.example.demo.service.AddressBookModel
import com.example.demo.service.AddressBookService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class MvcControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var addressBookService: AddressBookService

    private val addressBookModel = AddressBookModel("John Doe", "901-387-2344", "johndoe@example.com")

    @Test
    fun `verify list`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("list"))
    }

    @Test
    fun `verify view`() {
        addressBookService.add(addressBookModel)

        mockMvc.perform(MockMvcRequestBuilders.get("/app/${addressBookModel.id}/view"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("view"))
    }

    @Test
    fun `verify get edit`() {
        addressBookService.add(addressBookModel)

        mockMvc.perform(MockMvcRequestBuilders.get("/app/${addressBookModel.id}/edit"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("edit"))
    }

    @Test
    fun `verify add`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("name", addressBookModel.name)
                .param("phone", addressBookModel.phone)
                .param("email", addressBookModel.email)
        )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    @Test
    fun `verify post edit`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/${addressBookModel.name}/edit")
                .param("name", addressBookModel.name)
                .param("phone", addressBookModel.phone)
                .param("email", addressBookModel.email)
        )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    @Test
    fun `verify delete`() {
        addressBookService.add(addressBookModel)

        mockMvc.perform(MockMvcRequestBuilders.get("/app/${addressBookModel.id}/delete"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }
}