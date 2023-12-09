package org.example.springmvc.controller

import org.example.springmvc.entity.Contact
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
internal class AddressBookControllerTest {
    private lateinit var mockMvc: MockMvc
    private val expectedContact = Contact(id = 1, name = "Alex", city = "Moscow", phone = "+78007776655")

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun returnEmptyListTest() {
        assertFalse {
            sendGetListForm().andReturn()
                .response
                .contentAsString.matches(Regex(expectedContact.name!!))
        }
    }

    @Test
    fun addAddressRedirectTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("name", expectedContact.name)
                .param("city", expectedContact.city)
                .param("phone", expectedContact.phone)
        )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.redirectedUrl("/app/list"))

        sendGetListForm()
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedContact.name)))
    }

    @Test
    fun viewTest() {
        val contactId = 1
        mockMvc
            .perform(MockMvcRequestBuilders.get("/app/${contactId}/view"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("view"))
            .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedContact.name)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedContact.city)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedContact.phone)))
    }

    @Test
    fun editTest() {
        val contactId = 1
        mockMvc.perform(MockMvcRequestBuilders.get("/app/${contactId}/edit"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("edit"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedContact.name)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedContact.city)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedContact.phone)))
    }

    private fun sendGetListForm(): ResultActions = mockMvc
        .perform(MockMvcRequestBuilders.get("/app/list"))
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.view().name("list"))
        .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
}