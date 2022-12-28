package ru.sber.mvc

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginTest {

    @Autowired
    private lateinit var mock: MockMvc

    @Test
    fun `test login redirect`() {
        mock.perform(
            MockMvcRequestBuilders.post("/app/add")
            .param("name", "Jimmy")
            .param("phone", "7-956-489-51-62"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(redirectedUrl("/login"))
    }

}