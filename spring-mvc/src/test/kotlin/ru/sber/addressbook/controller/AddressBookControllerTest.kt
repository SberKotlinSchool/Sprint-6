package ru.sber.addressbook.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.sber.addressbook.data.Contact
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.Cookie

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressBookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private fun getAuthCookie(): Cookie = Cookie("auth",
        "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss"))}")


    @Test
    fun getAddressBookTest() {
        mockMvc.perform(get("/app/list").cookie(getAuthCookie()))
            .andExpect {
                status().isOk()
                content().string("addressbook")

            }
    }

    @Test
    fun createGETTest() {
        mockMvc.perform(get("/app/add").cookie(getAuthCookie()))
            .andExpect {
                status().isOk()
                content().string("contact")
            }
    }

    @Test
    fun createPOSTTest() {
        mockMvc.perform(post("/app/add").cookie(getAuthCookie())
            .flashAttr("contact", Contact(
                "Сидоров", "Александр", "Федорович",
                LocalDate.parse("10.02.2001", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                "+7 111 111-11-11", "sidorova@mail.ru"
            )
            ))
            .andExpect {
                status().is3xxRedirection
                redirectedUrl("/app/list")
            }
    }

    @Test
    fun editGETTest() {
        mockMvc.perform(get("/app/1/edit").cookie(getAuthCookie()))
            .andExpect {
                status().isOk()
                content().string("contact")
            }
    }


    @Test
    fun editPOSTTest() {
        mockMvc.perform(post("/app/1/edit").cookie(getAuthCookie())
            .flashAttr("contact", Contact(
                "Попов", "Иван", "Сергеевич",
                LocalDate.parse("01.01.1999", DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                "+7 333 333-33-33", "popovi@mail.ru"
            )
            ))
            .andExpect {
                status().is3xxRedirection
                redirectedUrl("/app/list")
            }
    }

    @Test
    fun readGETTest() {
        mockMvc.perform(get("/app/1/view").cookie(getAuthCookie()))
            .andExpect {
                status().isOk()
                content().string("contact")
            }
    }

    @Test
    fun deleteGETTest() {
        mockMvc.perform(get("/app/2/delete").cookie(getAuthCookie()))
            .andExpect {
                status().is3xxRedirection
                redirectedUrl("/app/list")
            }
    }
}

