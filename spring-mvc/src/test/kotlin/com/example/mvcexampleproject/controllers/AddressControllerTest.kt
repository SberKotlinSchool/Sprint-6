package com.example.mvcexampleproject.controllers

import org.hamcrest.Matchers.containsString
import com.example.mvcexampleproject.domain.Address
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
internal class AddressControllerTest {
    private lateinit var mockMvc: MockMvc
    private val testAddress = Address(name = "Pam Beesly", city = "Scampton", phone = "79990000000")

    @BeforeEach
    fun setup(webApplicationContext: WebApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun `method get to _app_list should return empty list`() {
        assertFalse {
            sendGetToAppList().andReturn()
                .response
                .contentAsString.matches(Regex(testAddress.name!!))
        }
    }

    @Test
    fun `method get to _app_add should return page with form add new address`() {
        mockMvc.perform(get("/app/add"))
            .andExpect(status().isOk)
            .andExpect(view().name("add"))
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andExpect(content().string(containsString("Add")))
    }

    @Test
    fun `method post to _app_add should add new address and execute redirect`() {
        mockMvc.perform(
            post("/app/add")
                .param("name", testAddress.name)
                .param("city", testAddress.city)
                .param("phone", testAddress.phone)
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))

        sendGetToAppList()
            .andExpect(content().string(containsString(testAddress.name)))
    }

    @Test
    fun `method get to _app_id_view should return view page`() {
        val addressId = 1
        sendGetToAppIdView(addressId)
            .andExpect(content().string(containsString(testAddress.name)))
            .andExpect(content().string(containsString(testAddress.city)))
            .andExpect(content().string(containsString(testAddress.phone)))
    }

    @Test
    fun `method get to _app_id_edit should return page edit`() {
        val addressId = 1
        mockMvc.perform(get("/app/${addressId}/edit"))
            .andExpect(status().isOk)
            .andExpect(view().name("edit"))
            .andExpect(content().string(containsString(testAddress.name)))
            .andExpect(content().string(containsString(testAddress.city)))
            .andExpect(content().string(containsString(testAddress.phone)))
    }

    @Test
    fun `method post to _app_id_edit should change address and execute redirect`() {
        val newPhone = "911"
        val addressId = 1
        mockMvc.perform(
            post("/app/${addressId}/edit")
                .param("name", testAddress.name)
                .param("city", testAddress.city)
                .param("phone", newPhone)
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))

        sendGetToAppIdView(addressId)
            .andExpect(content().string(containsString(newPhone)))
    }

    @Test
    fun `method post to _app_id_delete should remove address and execute redirect`() {
        val addressId = 1
        mockMvc.perform(post("/app/${addressId}/delete"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))

        assertFalse {
            sendGetToAppList().andReturn()
                .response
                .contentAsString.matches(Regex(testAddress.name!!))
        }
    }

    private fun sendGetToAppList(): ResultActions = mockMvc
        .perform(get("/app/list"))
        .andExpect(status().isOk)
        .andExpect(view().name("list"))
        .andExpect(content().contentType("text/html;charset=UTF-8"))

    private fun sendGetToAppIdView(id: Int) = mockMvc
        .perform(get("/app/${id}/view"))
        .andExpect(status().isOk)
        .andExpect(view().name("view"))
        .andExpect(content().contentType("text/html;charset=UTF-8"))
}