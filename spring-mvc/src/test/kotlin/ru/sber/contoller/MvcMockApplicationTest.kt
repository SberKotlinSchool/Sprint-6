package ru.sber.contoller

import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@SpringBootTest
@AutoConfigureMockMvc
class MvcMockApplicationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @Order(1)
    fun testAdd() {
        mockMvc.perform(
            post("/app/add")
                .param("address", "stub")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Успех!")))
            .andExpect(content().string(containsString("stub")))
    }

    @Test
    fun testList() {
        mockMvc.perform(
            get("/app/list")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Адреса")))
    }

    @Test
    @Order(2)
    fun testView() {
        mockMvc.perform(
            get("/app/0/view")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Куда уж подробнее?")))
    }

    @Test
    fun testEdit() {
        mockMvc.perform(
            post("/app/add")
                .param("address", "mock")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Успех!")))
            .andExpect(content().string(containsString("mock")))

        mockMvc.perform(
            post("/app/1/edit")
                .param("address", "test")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Адреса")))
            .andExpect(content().string(not(containsString("mock"))))
            .andExpect(content().string(containsString("test")))

    }

    @Test
    @Order(3)
    fun testDelete() {
        mockMvc.perform(
            post("/app/0/delete"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Адреса")))
            .andExpect(content().string(not(containsString("stub"))))
    }
}