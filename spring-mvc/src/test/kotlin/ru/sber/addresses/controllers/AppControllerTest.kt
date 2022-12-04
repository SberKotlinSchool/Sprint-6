package ru.sber.addresses.controllers

import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.stream.Stream

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AppControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val fullName = "Иванов Иван Иванович"
    private val postAddress = "г. Москва, Ленинский проспект, 32, кв. 18"
    private val phoneNumber = "+78000000000"
    private val email = "test@test.ru"

    var counter = 0

    @Test
    fun list() {
        addAddress().also { counter++ }
        checkListRequestPerform(MockMvcRequestBuilders.get("/app/list"))
        checkListRequestPerform(MockMvcRequestBuilders.get("/app/list").param("fullName", fullName))
    }

    @Test
    fun addAddressGetTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("add"))
    }

    @Test
    fun addCustomerPostSuccess() {
        addAddress()
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
            .also { counter++ }

    }

    @ParameterizedTest
    @MethodSource("getDataForAddAddressWhenDataNotValid")
    fun addAddressWhenDataNotValid(fullName: String?, postAddress: String?, phoneNumber: String?, email: String?) {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("fullName", fullName)
                .param("postAddress", postAddress)
                .param("phoneNumber", phoneNumber)
                .param("email", email)
        )
            .andExpect(MockMvcResultMatchers.view().name("add"))
    }

    @ParameterizedTest
    @MethodSource("getDataForAddAddressWhenDataNotValid")
    fun editAddressWhenDataNotValid(fullName: String?, postAddress: String?, phoneNumber: String?, email: String?) {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/$counter/edit")
                .param("fullName", fullName)
                .param("postAddress", postAddress)
                .param("phoneNumber", phoneNumber)
                .param("email", email)
        )
            .andExpect(MockMvcResultMatchers.view().name("edit"))
    }

    @Test
    fun deleteCustomersSuccess() {
        addAddress().also { counter++ }
        mockMvc.perform(MockMvcRequestBuilders.get("/app/$counter/delete"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    @Test
    fun viewAddressGetTest() {
        addAddress().also { counter++ }
        mockMvc.perform(MockMvcRequestBuilders.get("/app/$counter/view"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("view"))
    }

    @Test
    fun viewAddressGetTestWhenRedirection() {
        addAddress().also { counter++ }
        mockMvc.perform(MockMvcRequestBuilders.get("/app/${counter + 1}/view"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    @Test
    fun editAddressGetTest() {
        addAddress().also { counter++ }
        mockMvc.perform(MockMvcRequestBuilders.get("/app/$counter/edit"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("edit"))
    }

    @Test
    fun editAddressGetTestWhenRedirection() {
        addAddress().also { counter++ }
        mockMvc.perform(MockMvcRequestBuilders.get("/app/${counter + 1}/edit"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    @Test
    fun editAddress() {
        addAddress().also { counter++ }
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/$counter/edit")
                .param("fullName", "${fullName}_edited")
                .param("postAddress", postAddress)
                .param("phoneNumber", phoneNumber)
                .param("email", email)
        )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/app/list")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("${fullName}_edited")))
    }

    private fun getDataForAddAddressWhenDataNotValid() = Stream.of(
        Arguments.of("", postAddress, phoneNumber, email),
        Arguments.of(fullName, postAddress, phoneNumber, "wrongEmail")
    )

    private fun addAddress() =
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("fullName", fullName)
                .param("postAddress", postAddress)
                .param("phoneNumber", phoneNumber)
                .param("email", email)
        )

    private fun checkListRequestPerform(requestBuilder: RequestBuilder) {
        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("list"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(fullName)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(postAddress)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(phoneNumber)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(email)))
    }
}