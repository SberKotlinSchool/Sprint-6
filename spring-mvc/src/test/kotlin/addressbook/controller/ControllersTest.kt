package addressbook.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.Cookie

@ExtendWith(MockitoExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
class ControllersTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `test viewHomePage`() {
        mockMvc.perform(get("/app/list").cookie(authenticatedCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
    }

    @Test
    fun `test showNewPersonForm`() {
        mockMvc.perform(get("/app/showNewPersonForm").cookie(authenticatedCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("new_person"))
    }

    @Test
    fun `test addPerson`() {
        mockMvc.perform(post("/app/add")
            .param("name", "Test Name")
            .param("address", "Test Address")
            .cookie(authenticatedCookie()))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }

    @Test
    fun `test showFormForUpdate`() {
        mockMvc.perform(get("/app/showFormForUpdate/1").cookie(authenticatedCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("update_person"))
    }

    @Test
    fun `test editPerson`() {
        mockMvc.perform(post("/app/edit?additionalField=1")
            .param("name", "Test Name")
            .param("address", "Test Address")
            .cookie(authenticatedCookie()))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }

    @Test
    fun `test showFormView`() {
        mockMvc.perform(get("/app/showFormView/1").cookie(authenticatedCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("view_person"))
    }

    @Test
    fun `test deletePerson`() {
        mockMvc.perform(get("/app/delete/1").cookie(authenticatedCookie()))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }

    private fun authenticatedCookie(): Cookie {
        return Cookie("auth", LocalDateTime.now().plusMinutes(10).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }
}