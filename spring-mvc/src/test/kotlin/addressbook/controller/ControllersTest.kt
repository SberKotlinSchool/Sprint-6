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
import org.springframework.security.test.context.support.WithMockUser

@ExtendWith(MockitoExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
class ControllersTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @WithMockUser(value = "admin", roles = ["ADMIN"])
    fun `test viewHomePageWithAdmin`() {
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
    }

    @Test
    @WithMockUser(value = "user", roles = ["USER"])
    fun `test viewHomePageWithUser`() {
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
    }

    @Test
    fun `test viewHomePageWithNotAuthorize`() {
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser(value = "api", roles = ["API"])
    fun `test viewHomePageWithIsForbidden`() {
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isForbidden)
    }


    @Test
    @WithMockUser(value = "user", roles = ["USER"])
    fun `test showNewPersonForm`() {
        mockMvc.perform(get("/app/showNewPersonForm"))
            .andExpect(status().isOk)
            .andExpect(view().name("new_person"))
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `test addPerson`() {
        mockMvc.perform(post("/app/add")
            .param("name", "Test Name")
            .param("address", "Test Address"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }

    @Test
    @WithMockUser(value = "user", roles = ["USER"])
    fun `test showFormForUpdate`() {
        mockMvc.perform(get("/app/showFormForUpdate/1"))
            .andExpect(status().isOk)
            .andExpect(view().name("update_person"))
    }

    @Test
    @WithMockUser(value = "user", roles = ["USER"])
    fun `test editPerson`() {
        mockMvc.perform(post("/app/edit?additionalField=1")
            .param("name", "Test Name")
            .param("address", "Test Address"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }

    @Test
    @WithMockUser(value = "user", roles = ["USER"])
    fun `test showFormView`() {
        mockMvc.perform(get("/app/showFormView/1"))
            .andExpect(status().isOk)
            .andExpect(view().name("view_person"))
    }

    @Test
    @WithMockUser(value = "user", roles = ["USER"])
    fun `test deletePerson`() {
        mockMvc.perform(get("/app/delete/1"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }

}