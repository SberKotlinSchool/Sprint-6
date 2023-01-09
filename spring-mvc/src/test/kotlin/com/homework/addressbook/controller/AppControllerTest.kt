package com.homework.addressbook.controller

import com.homework.addressbook.dto.Record
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import java.time.LocalDateTime
import javax.servlet.http.Cookie

@AutoConfigureMockMvc
@SpringBootTest
class AppControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun addRecord() {
        mockMvc.perform( MockMvcRequestBuilders.post("/app/add")
            .cookie(Cookie("auth", LocalDateTime.now().toString()))
            .flashAttr("record", Record("TEST","TEST","TEST","TEST")))
            .andExpect(view().name("redirect:/app/list"))
    }

    @Test
    fun getRecords() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/list")
                .cookie(Cookie("auth", LocalDateTime.now().toString())))
            .andExpect(view().name("list"))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().string(containsString("Падме")))
    }

    @Test
    fun getCurrentRecord() {
        mockMvc.perform( MockMvcRequestBuilders.get("/app/2/view")
                .cookie(Cookie("auth", LocalDateTime.now().toString())))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().string(containsString("Лея")))
    }

    @Test
    fun editRecordView() {
        mockMvc.perform( MockMvcRequestBuilders.get("/app/3/edit")
            .cookie(Cookie("auth", LocalDateTime.now().toString())))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().string(containsString("Хан")))
    }

    @Test
    fun editRecordSubmit() {
        mockMvc.perform( MockMvcRequestBuilders.post("/app/3/edit")
            .cookie(Cookie("auth", LocalDateTime.now().toString()))
            .flashAttr("record", Record("Хан","Соло","TEST","TEST")))
            .andExpect(view().name("redirect:/app/list"))
    }

    @Test
    fun deleteRecord() {
        mockMvc.perform( MockMvcRequestBuilders.delete("/app/3/delete")
            .cookie(Cookie("auth", LocalDateTime.now().toString())))
            .andExpect(view().name("redirect:/app/list"))
    }
}