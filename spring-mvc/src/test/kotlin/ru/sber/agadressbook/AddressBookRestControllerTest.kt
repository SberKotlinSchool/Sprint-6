package ru.sber.agadressbook

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.io.File


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressBookRestControllerTest {

    private val host: String = "localhost"
    @LocalServerPort
    private val port: Int = 0

    @Autowired
    lateinit var httpMock: MockMvc
    private val newRecordJson = """{"id":"555","firstName":"Аристарх", "phoneNumber":"111-11-11", "address": "переулок Сивцев Вражек"}"""


    private fun getUrl(endPoint: String, isSecured: Boolean): String {
        return if (isSecured) {
            "https://$host:$port/$endPoint"
        } else "http://$host:$port/$endPoint"
    }


    @WithMockUser(username = "admin", password = "123", roles = ["API"])
    @Test()
    fun viewRecordTest() {

        httpMock.perform(
            MockMvcRequestBuilders.get(getUrl("/addressbook/api/2/view", false))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.phoneNumber").value("777-77-77"))
    }

    @WithMockUser(username = "admin", password = "123", roles = ["API"])
    @Test()
    fun getRecordListTest() {

        httpMock.perform(
            MockMvcRequestBuilders.get(getUrl("/addressbook/api/list", false))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$[0].firstName").value("Пиастр"))
    }

    @Test
    @WithMockUser(username = "admin", password = "123", roles = ["API"])
    fun addRecordTest() {

        httpMock.perform(
            MockMvcRequestBuilders.post(getUrl("/addressbook/api/add", false))
                .content(newRecordJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(jsonPath("$.firstName").value("Аристарх"))
            .andExpect(jsonPath("$.address").value("переулок Сивцев Вражек"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser(username = "superadmin", password = "123", roles = ["ADMIN"])
    fun deleteRecordTestPositive() {

        httpMock.perform(
            MockMvcRequestBuilders.delete("/addressbook/api/1/delete")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser(username = "admin", password = "123", roles = ["API"])
    fun deleteRecordTestNegative() {

        httpMock.perform(
            MockMvcRequestBuilders.delete("/addressbook/api/1/delete")
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    @WithMockUser(username = "admin", password = "123", roles = ["API"])
    fun editRecordTest() {

        val editRecordJson = """{"id":"1","firstName":"Пиастр второй", "phoneNumber":"333-33-33", "address": "Редактируемая улица"}"""

        httpMock.perform(
            MockMvcRequestBuilders.post(getUrl("/addressbook/api/add", false))
                .content(editRecordJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(jsonPath("$.firstName").value("Пиастр второй"))
            .andExpect(jsonPath("$.address").value("Редактируемая улица"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}