package ru.sber.springmvc

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import java.time.LocalDateTime
import java.util.Collections.singletonList
import org.springframework.http.*
import org.springframework.http.client.ClientHttpRequestInterceptor
import ru.sber.springmvc.domain.Record
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringMvcKotlinApplicationTests {

    @Autowired
    private lateinit var httpMock: MockMvc

    private val record = Record(3, "Pushkin's library", "Kamennoostrovskiy, 35")

    @Test
    fun shouldCheckRecordWasAdded() {
        httpMock.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("id", record.id.toString())
                .param("name", record.name)
                .param("address", record.address)
        )
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun shouldCheckList() {
        httpMock.perform(
            MockMvcRequestBuilders.get("/app/list")
        )
            .andExpect(MockMvcResultMatchers.view().name("showAll"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun shouldCheckGetById() {
        httpMock.perform(
            MockMvcRequestBuilders.get("/app/2/view")
        )
            .andExpect(MockMvcResultMatchers.view().name("getById"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun shouldCheckDeleteById() {
        httpMock.perform(
            MockMvcRequestBuilders.get("/app/2/delete")
        )
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }

    @Test
    fun shouldCheckEditById() {
        httpMock.perform(
            MockMvcRequestBuilders.get("/app/2/delete")
        )
            .andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
    }
}
