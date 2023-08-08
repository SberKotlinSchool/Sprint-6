package com.example.springmvc.controller.mvc

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import java.io.File

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class AppAddressBookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    val correctId = "1"
    val correctName = "Jennifer"
    val incorrectName = null

    val incorrectId = "10"
    val correctPhone = "333-33-33"
    val incorrectPhone = null

    @Test
    fun listAllContactsTest() {
        val list = File("src/test/resources/list").readText()

        mockMvc.perform(get("/app/list"))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                view().name("list"),
                content().string(list)
            )
    }

    @Test
    fun viewContactTest() {
        mockMvc.perform(get("/app/{id}/view", correctId))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                view().name("view"))

        mockMvc.perform(get("/app/{id}/view", incorrectId))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                view().name("contactError"))
    }

    @Test
    fun editContactSuccessTest() {
        mockMvc.perform(get("/app/{id}/edit", correctId))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                view().name("edit")
            )
    }

    @Test
    fun editContactErrorTest() {
        mockMvc.perform(get("/app/{id}/edit", incorrectId))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                view().name("contactError")
            )
    }

    @Test
    fun editSuccessTest() {
        mockMvc.perform(
            patch("/app/{id}/edit", correctId)
                .param("name", correctName)
                .param("phone", correctPhone))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                view().name("view")
            )
    }

    @Test
    fun editErrorTest() {
        mockMvc.perform(
            patch("/app/{id}/edit", correctId)
                .param("name", incorrectName)
                .param("phone", incorrectPhone))
            .andDo(print())
            .andExpectAll(
                model().attributeHasErrors("contact"),
                status().isOk,
                view().name("edit")
            )
    }

    @Test
    fun deleteContactTest()
    {
        mockMvc.perform(delete("/app/{id}/delete", correctId))
            .andDo(print())
            .andExpectAll(
                status().is3xxRedirection,
                view().name("redirect:/app/list")
            )

        mockMvc.perform(delete("/app/{id}/delete", incorrectId))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                view().name("contactError")
            )
    }

    @Test
    fun addContactTest() {
        mockMvc.perform(get("/app/add"))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                view().name("add")
            )
    }

    @Test
    fun addSuccessTest() {
        mockMvc.perform(
            post("/app/add")
                .param("name", correctName)
                .param("phone", correctPhone))
            .andDo(print())
            .andExpectAll(
                status().is3xxRedirection,
                view().name("redirect:/app/list")
            )

    }

    @Test
    fun addErrorTest() {
        mockMvc.perform(
            post("/app/add")
                .param("name", incorrectName)
                .param("phone", incorrectPhone))
            .andDo(print())
            .andExpectAll(
                status().isOk,
                model().attributeHasErrors("contact"),
                view().name("add")
            )
    }
}