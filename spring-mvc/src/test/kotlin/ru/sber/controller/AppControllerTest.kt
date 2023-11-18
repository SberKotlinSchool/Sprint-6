package ru.sber.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.sber.model.Note
import ru.sber.repository.AddressBookRepository
import java.time.LocalDateTime
import javax.servlet.http.Cookie
import kotlin.test.assertEquals
import kotlin.test.assertNull

@SpringBootTest
@AutoConfigureMockMvc
internal class AppControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var repository: AddressBookRepository

    private val cookie = Cookie("auth", "${LocalDateTime.now()}")

    private val testNote = Note("NAME", "ADDRESS", "+89999999999")
    private val testNote2 = Note("NAME2", "ADDRESS2", "+82222222222")

    @BeforeEach
    fun cleanBase() {
        repository.deleteAll()
    }

    @ParameterizedTest
    @ValueSource(strings = ["/app/add", "/app/list", "/app/0/view", "/app/0/edit", "/app/0/delete"])
    fun `should login`(url: String) {
        //given
        repository.create(testNote)
        //when
        mockMvc.perform(
            MockMvcRequestBuilders.get(url)
                .flashAttr("note", testNote)
        )
            //then
            .andExpect {
                MockMvcResultMatchers.status().is3xxRedirection()
                MockMvcResultMatchers.redirectedUrl("/login")
            }
    }

    @Test
    fun add() {
        //given
        //when
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/app/add")
                .cookie(cookie)
                .flashAttr("note", testNote)
        )
            //then
            .andExpect {
                MockMvcResultMatchers.status().is3xxRedirection()
                MockMvcResultMatchers.redirectedUrl("/app/list")
            }
        assertEquals(testNote, repository.getById(0L))
    }

    @Test
    fun list() {
        //given
        repository.create(testNote)
        repository.create(testNote2)
        //when
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/app/list")
                .cookie(cookie)
        )
            //then
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.model().attribute("notes", listOf(testNote, testNote2)))
    }

    @Test
    fun view() {
        //given
        repository.create(testNote)
        repository.create(testNote2)
        //when
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/app/0/view")
                .cookie(cookie)
        )
            //then
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.model().attribute("note", testNote))
    }

    @Test
    fun edit() {
        //given
        repository.create(testNote)
        //when
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/app/0/edit")
                .cookie(cookie)
                .flashAttr("note", testNote2)
        )
            //then
            .andExpect {
                MockMvcResultMatchers.status().is3xxRedirection()
                MockMvcResultMatchers.redirectedUrl("/app/0/view")
            }
            .andExpect(MockMvcResultMatchers.model().attribute("note", testNote2))
        assertEquals(testNote2, repository.getById(0L))
    }

    @Test
    fun delete() {
        //given
        repository.create(testNote)
        repository.create(testNote2)
        //when
        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/app/0/delete")
                .cookie(cookie)
        )
            //then
            .andExpect {
                MockMvcResultMatchers.status().is3xxRedirection()
                MockMvcResultMatchers.redirectedUrl("/app/list")
            }
        assertEquals(1, repository.getAll().size)
        assertNull(repository.getById(0L))
        assertEquals(testNote2, repository.getById(1L))
    }
}