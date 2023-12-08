package ru.sber.controller

import jakarta.servlet.http.Cookie
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.sber.model.BaseEntity
import java.time.LocalDateTime

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MvcControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun showAddBaseEntity() {
        mockMvc.perform(
            get("/app/add")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("add"))
    }

    @Test
    fun showAddBaseEntityWithoutAuth() {
        mockMvc.perform(
            get("/app/add")
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun addBaseEntity() {
        mockMvc.perform(
            post("/app/add")
                .cookie(getAuthCookie())
                .flashAttr("baseEntity", BaseEntity(name = "Name", address = "Address"))
        )
            .let { expectMainPageRedirect(it) }
    }

    @Test
    fun addBaseEntityWithoutAuth() {
        mockMvc.perform(
            post("/app/add")
                .flashAttr("baseEntity", BaseEntity(name = "Name", address = "Address"))
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun getBaseEntities() {
        mockMvc.perform(
            get("/app/list")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
    }

    @Test
    fun getBaseEntitiesWithoutAuth() {
        mockMvc.perform(
            get("/app/list")
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun viewBaseEntity() {
        mockMvc.perform(
            get("/app/0/view")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("view"))
    }

    @Test
    fun viewNonExistingBaseEntity() {
        mockMvc.perform(
            get("/app/2/view")
                .cookie(getAuthCookie())
        )
            .let { expectMainPageRedirect(it) }
    }

    @Test
    fun viewBaseEntityWithoutAuth() {
        mockMvc.perform(
            get("/app/1/view")
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun showEditBaseEntity() {
        mockMvc.perform(
            get("/app/1/edit")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("edit"))
    }

    @Test
    fun showEditNonExistingBaseEntity() {
        mockMvc.perform(
            get("/app/2/edit")
                .cookie(getAuthCookie())
        )
            .let { expectMainPageRedirect(it) }
    }

    @Test
    fun showEditBaseEntityWithoutAuth() {
        mockMvc.perform(
            get("/app/1/edit")
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun editBaseEntity() {
        mockMvc.perform(
            post("/app/1/edit")
                .cookie(getAuthCookie())
                .flashAttr("baseEntity", BaseEntity(name = "Name", address = "Address"))
        )
            .let { expectMainPageRedirect(it) }
    }

    @Test
    fun editBaseEntityWithoutAuth() {
        mockMvc.perform(
            post("/app/1/edit")
                .flashAttr("baseEntity", BaseEntity(name = "Name", address = "Address"))
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun deleteBaseEntity() {
        mockMvc.perform(
            get("/app/2/delete")
                .cookie(getAuthCookie())
        )
            .let { expectMainPageRedirect(it) }
    }

    @Test
    fun deleteBaseEntityWithoutAuth() {
        mockMvc.perform(
            get("/app/1/delete")
        )
            .let { expectLoginRedirect(it) }
    }

    private fun getAuthCookie(): Cookie {
        return Cookie("auth1", "${LocalDateTime.now().plusMinutes(5)}")
    }

    private fun expectLoginRedirect(resultActions: ResultActions) {
        resultActions
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/login"))
    }

    private fun expectMainPageRedirect(resultActions: ResultActions) {
        resultActions
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/list"))
    }
}