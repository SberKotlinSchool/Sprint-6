package com.firebat.addressbook.controller

import com.firebat.addressbook.model.Entry
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
import java.time.LocalDateTime

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MvcControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun showAddEntry() {
        mockMvc.perform(
            get("/app/add")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("add"))
    }

    @Test
    fun showAddEntryWithoutAuth() {
        mockMvc.perform(
            get("/app/add")
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun addEntry() {
        mockMvc.perform(
            post("/app/add")
                .cookie(getAuthCookie())
                .flashAttr("entry", Entry(name = "Name", address = "Address"))
        )
            .let { expectMainPageRedirect(it) }
    }

    @Test
    fun addEntryWithoutAuth() {
        mockMvc.perform(
            post("/app/add")
                .flashAttr("entry", Entry(name = "Name", address = "Address"))
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun getEntries() {
        mockMvc.perform(
            get("/app/list")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
    }

    @Test
    fun getEntriesWithoutAuth() {
        mockMvc.perform(
            get("/app/list")
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun viewEntry() {
        mockMvc.perform(
            get("/app/0/view")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("view"))
    }

    @Test
    fun viewNonExistingEntry() {
        mockMvc.perform(
            get("/app/2/view")
                .cookie(getAuthCookie())
        )
            .let { expectMainPageRedirect(it) }
    }

    @Test
    fun viewEntryWithoutAuth() {
        mockMvc.perform(
            get("/app/1/view")
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun showEditEntry() {
        mockMvc.perform(
            get("/app/1/edit")
                .cookie(getAuthCookie())
        )
            .andExpect(status().isOk)
            .andExpect(view().name("edit"))
    }

    @Test
    fun showEditNonExistingEntry() {
        mockMvc.perform(
            get("/app/2/edit")
                .cookie(getAuthCookie())
        )
            .let { expectMainPageRedirect(it) }
    }

    @Test
    fun showEditEntryWithoutAuth() {
        mockMvc.perform(
            get("/app/1/edit")
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun editEntry() {
        mockMvc.perform(
            post("/app/1/edit")
                .cookie(getAuthCookie())
                .flashAttr("entry", Entry(name = "Name", address = "Address"))
        )
            .let { expectMainPageRedirect(it) }
    }

    @Test
    fun editEntryWithoutAuth() {
        mockMvc.perform(
            post("/app/1/edit")
                .flashAttr("entry", Entry(name = "Name", address = "Address"))
        )
            .let { expectLoginRedirect(it) }
    }

    @Test
    fun deleteEntry() {
        mockMvc.perform(
            get("/app/2/delete")
                .cookie(getAuthCookie())
        )
            .let { expectMainPageRedirect(it) }
    }

    @Test
    fun deleteEntryWithoutAuth() {
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