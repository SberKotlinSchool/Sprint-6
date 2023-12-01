package com.dokl57.springmvc.controller

import jakarta.servlet.http.Cookie
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(MockitoExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
class MvcControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `test showAddEntry`() {
        mockMvc.perform(get("/app/add").cookie(authenticatedCookie()))
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))
    }

    @Test
    fun `test addEntry`() {
        mockMvc.perform(post("/app/add")
            .param("name", "Test Name")
            .param("address", "Test Address")
            .cookie(authenticatedCookie()))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }

    @Test
    fun `test getEntries`() {
        mockMvc.perform(get("/app/list").cookie(authenticatedCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
    }

    @Test
    fun `test viewEntry`() {
        mockMvc.perform(get("/app/0/view").cookie(authenticatedCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("view"))
    }

    @Test
    fun `test showEditEntry`() {
        mockMvc.perform(get("/app/1/edit").cookie(authenticatedCookie()))
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))
    }

    @Test
    fun `test editEntry`() {
        mockMvc.perform(post("/app/1/edit")
            .param("name", "Updated Name")
            .param("address", "Updated Address")
            .cookie(authenticatedCookie()))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }

    @Test
    fun `test deleteEntry`() {
        mockMvc.perform(get("/app/1/delete"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }

    private fun authenticatedCookie(): Cookie {
        return Cookie("auth", LocalDateTime.now().plusMinutes(5).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }
}
