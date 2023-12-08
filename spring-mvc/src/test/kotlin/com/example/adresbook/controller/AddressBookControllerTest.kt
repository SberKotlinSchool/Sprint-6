package com.example.adresbook.controller

import com.example.adresbook.AddressBookRepository
import com.example.adresbook.model.BookRecord
import jakarta.servlet.http.Cookie
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AddressBookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var bookRepository: AddressBookRepository

    private val cookie = Cookie("auth", LocalDateTime.now().toString())

    @BeforeEach
    fun bdUpdate(){
        bookRepository = AddressBookRepository()
    }


    @Test
    fun add() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/v1/add")
                .cookie(cookie)
                .flashAttr("record", BookRecord(4, "Проспект Ленина 4", "4"))
        )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/v1/list"))
    }


    @Test
    fun list() {
        val result = mockMvc
            .perform(
                get("/app/v1/list")
                    .cookie(cookie)
            )
            .andExpect(status().is2xxSuccessful)
            .andExpect(model().attributeExists("records"))
            .andReturn()

        assertEquals(bookRepository.pseudoDataBase, result.modelAndView?.model?.get("records"));
    }

    @Test
    fun `view`() {
        val result = mockMvc
            .perform(
                get("/app/v1/1/view")
                    .cookie(cookie)
            )
            .andExpect(status().is2xxSuccessful)
            .andExpect(model().attributeExists("record"))
            .andReturn()

        assertEquals(bookRepository.pseudoDataBase[0], result.modelAndView?.model?.get("record"));
    }

    @Test
    fun edit() {
        val result = mockMvc
            .perform(
                get("/app/v1/3/edit")
                    .cookie(cookie)
            )
            .andExpect(status().is2xxSuccessful)
            .andReturn()

        assertEquals(bookRepository.pseudoDataBase[2], result.modelAndView?.model?.get("record"))
    }

    @Test
    fun editPost() {
        mockMvc
            .perform(
                post("/app/v1/2/edit")
                    .cookie(cookie)
                    .flashAttr("record", BookRecord(4, "Проспект Ленина 1", "4"))
            )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/v1/list"))

    }

    @Test
    fun delete() {
        mockMvc
            .perform(
                get("/app/v1/1/delete")
                    .cookie(cookie)
            )
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/app/v1/list"))
    }
}