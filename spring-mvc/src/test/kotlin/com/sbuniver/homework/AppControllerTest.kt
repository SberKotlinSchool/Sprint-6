package com.sbuniver.homework

import org.hamcrest.Matchers.not
import org.hamcrest.core.StringContains
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.servlet.function.RequestPredicates.param

@SpringBootTest
@AutoConfigureMockMvc
class AppControllerTest {

    @Autowired
    private lateinit var mock: MockMvc

    @Test
    fun getAllAddressesTest() {
        mock.perform(MockMvcRequestBuilders.get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(StringContains("Andrey")))
    }

    @Test
    fun getOneAddressTest() {
        mock.perform(MockMvcRequestBuilders.get("/app/2/view"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(StringContains("Alexey")))
            .andExpect(MockMvcResultMatchers.content().string(StringContains("Sochi")))
    }

    @Test
    fun addAddressTest() {
        mock
            .perform(
                MockMvcRequestBuilders
                    .post("/app/add")
                    .param("name", "Newman")
                    .param("city", "New York")
                    .param("street", "New Street")
                    .param("home", "100")

            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)

        mock.perform(MockMvcRequestBuilders.get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(StringContains("Newman")))
            .andExpect(MockMvcResultMatchers.content().string(StringContains("New York")))
            .andExpect(MockMvcResultMatchers.content().string(StringContains("New Street")))
    }

    @Test
    fun deleteAddressTest() {
        mock.perform(MockMvcRequestBuilders.get("/app/list"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content().string(StringContains("Andrey")))

        mock
            .perform(MockMvcRequestBuilders.delete("/app/1/delete"))
            .andDo(MockMvcResultHandlers.print())

        mock.perform(MockMvcRequestBuilders.get("/app/list"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content().string(not(StringContains("Andrey"))))
    }
}