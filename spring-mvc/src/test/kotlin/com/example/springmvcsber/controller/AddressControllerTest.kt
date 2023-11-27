package com.example.springmvcsber.controller

import com.example.springmvcsber.entity.Address
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertFalse
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
class AddressControllerTest {
    private lateinit var mockMvc: MockMvc
    private val testAddress = Address(name = "Somename", city = "Moscow", phone = "123123")

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun returnEmptyListTest() {
        assertFalse {
            sendGetListForm().andReturn()
                .response
                .contentAsString.matches(Regex(testAddress.name!!))
        }
    }

    @Test
    fun addAddressRedirectTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("name", testAddress.name)
                .param("city", testAddress.city)
                .param("phone", testAddress.phone)
        )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.redirectedUrl("/app/list"))

        sendGetListForm()
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(testAddress.name)))
    }

    @Test
    fun viewTest() {
        val addressId = 1
        mockMvc
            .perform(MockMvcRequestBuilders.get("/app/${addressId}/view"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("view"))
            .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(testAddress.name)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(testAddress.city)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(testAddress.phone)))
    }

    @Test
    fun editTest() {
        val addressId = 1
        mockMvc.perform(MockMvcRequestBuilders.get("/app/${addressId}/edit"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("edit"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(testAddress.name)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(testAddress.city)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(testAddress.phone)))
    }

    private fun sendGetListForm(): ResultActions = mockMvc
        .perform(MockMvcRequestBuilders.get("/app/list"))
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.view().name("list"))
        .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
}