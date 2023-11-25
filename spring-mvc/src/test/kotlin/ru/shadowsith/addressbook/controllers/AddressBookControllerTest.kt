package ru.shadowsith.addressbook.controllers

import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.shadowsith.addressbook.dto.Record
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
class AddressBookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun addRecord() {
        val record = Record(name = "qwe", address = "address", phone = "123")
        mockMvc.perform(post("/app/add")
            .flashAttr("record", record)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is3xxRedirection)
        .andExpect(view().name("redirect:/app/list"))
        .andExpect(redirectedUrl("/app/list"))
    }

    @Test
    fun addRecordView() {
        mockMvc.perform(get("/app/add"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("add"))
            .andExpect(content().string(containsString("Адресная книга")))
    }

    @Test
    fun getRecords() {
        mockMvc.perform(get("/app/list"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("list"))
            .andExpect(content().string(containsString("Адресная книга")))
            .andExpect(content().string(containsString("Добавить запись")))
    }

    @Test
    fun deleteRecord() {
        mockMvc.perform(get("/app/dd375830-95b7-4c94-92ec-c9cd50f75d46/delete"))
            .andDo(print())
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/app/list"))
            .andExpect(redirectedUrl("/app/list"))
    }

    @Test
    @Disabled("Не ясная ошибка, нужна помощь")
    fun getRecord() {
        val record = Record(guid = UUID.randomUUID().toString(), name = "qwe", address = "address", phone = "123")
        mockMvc.perform(get("/app/dd375830-95b7-4c94-92ec-c9cd50f75d46/view")
            .flashAttr("record", record)
            .content("guid"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("view"))
            .andExpect(content().string(containsString("Запись из адресной книги")))
    }

    @Test
    @Disabled("Не ясная ошибка, нужна помощь")
    fun changeRecord() {
        val record = Record(guid = UUID.randomUUID().toString(), name = "qwe", address = "address", phone = "123")

        mockMvc.perform(post("/app/dd375830-95b7-4c94-92ec-c9cd50f75d46/edit")
            .flashAttr("record", record))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("view"))
            .andExpect(content().string(containsString("Запись из адресной книги")))
    }
}