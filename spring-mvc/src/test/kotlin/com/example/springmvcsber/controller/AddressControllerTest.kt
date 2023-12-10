package com.example.springmvcsber.controller

import com.example.springmvcsber.entity.Address
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matchers
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@AutoConfigureMockMvc
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
            post("/app/add")
                .param("name", testAddress.name)
                .param("city", testAddress.city)
                .param("phone", testAddress.phone)
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))

        sendGetListForm()
            .andExpect(content().string(Matchers.containsString(testAddress.name)))
    }

    @WithMockUser(username = "user", password = "pass", roles = ["ADMIN", "API"])
    @Test
    fun returnEmptyListSecurityTest() {
        assertFalse {
            sendGetListForm().andReturn()
                .response
                .contentAsString.matches(Regex(testAddress.name!!))
        }
    }

    @WithMockUser(username = "user", password = "pass", roles = ["ADMIN", "API"])
    @Test
    fun addGetSecurityTest() {
        mockMvc.perform(get("/app/add"))
            .andExpect(status().isOk)
            .andExpect(view().name("add"))
    }

    @WithMockUser(username = "user", password = "pass", roles = ["ADMIN", "API"])
    @Test
    fun addAddressRedirectSecurityTest() {
        mockMvc.perform(
            post("/app/add")
                .param("name", testAddress.name)
                .param("city", testAddress.city)
                .param("phone", testAddress.phone)
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))

        sendGetListForm()
            .andExpect(content().string(containsString(testAddress.name)))
    }

    @WithMockUser(username = "user", password = "pass", roles = ["ADMIN"])
    @Test
    fun deleteAddressRedirectSecurityTest() {
        val addressId = 1L
        mockMvc.perform(post("/app/${addressId}/delete"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))

        assertFalse {
            sendGetListForm().andReturn()
                .response
                .contentAsString.matches(Regex(testAddress.name!!))
        }
    }

    private fun sendGetListForm(): ResultActions = mockMvc
        .perform(get("/app/list"))
        .andExpect(status().isOk)
        .andExpect(view().name("list"))
        .andExpect(content().contentType("text/html;charset=UTF-8"))

}