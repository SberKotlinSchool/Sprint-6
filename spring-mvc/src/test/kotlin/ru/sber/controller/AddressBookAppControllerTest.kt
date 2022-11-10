package ru.sber.controller

import org.hamcrest.Matchers
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
internal class AddressBookAppControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val fio = "Testov Test Testovich"
    private val address = "Address"
    private val phone = "1234567890"
    private val email = "testov@mail.ru"

    @Test
    fun addCustomerGetTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("add"))
    }

    @Test
    fun addCustomerPostSuccess() {
        addTestCustomer()
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))

    }

    @ParameterizedTest
    @MethodSource("getDataForAddCustomerTestWhenDataNotValid")
    fun addCustomerTestWhenDataNotValid(fio: String?, address: String?, phone: String?, email: String?) {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("fio", fio)
                .param("address", address)
                .param("phone", phone)
                .param("email", email)
        )
            .andExpect(MockMvcResultMatchers.view().name("add"))
    }

    private fun getDataForAddCustomerTestWhenDataNotValid() = Stream.of(
        Arguments.of("", address, phone, email),
        Arguments.of(fio, address, phone, "test@")
    )

    @Test
    fun listCustomersSuccess() {
        addTestCustomer()
        checkListRequestPerform(MockMvcRequestBuilders.get("/app/list"))
        checkListRequestPerform(MockMvcRequestBuilders.get("/app/list").param("fio", fio))
    }

    @Test
    fun deleteCustomersSuccess() {
        addTestCustomer()
        mockMvc.perform(MockMvcRequestBuilders.get("/app/1/delete"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    @Test
    fun editCustomersSuccess() {
        addTestCustomer()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/1/edit")
                .param("fio", fio + "Edit")
                .param("address", address)
                .param("phone", phone)
                .param("email", email)
        )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/app/list")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(fio + "Edit")))
    }

    private fun addTestCustomer() =
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("fio", fio)
                .param("address", address)
                .param("phone", phone)
                .param("email", email)
        )


    private fun checkListRequestPerform(requestBuilder: RequestBuilder) {
        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("list"))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(fio)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(address)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(phone)))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(email)))
    }
}

