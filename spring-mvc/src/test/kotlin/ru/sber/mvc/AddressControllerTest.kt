package ru.sber.mvc

import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import javax.servlet.http.Cookie

@AutoConfigureMockMvc
@SpringBootTest
//@TestInstance(PER_CLASS)
class AddressControllerTest {

    @Autowired
    private lateinit var mock: MockMvc

    private fun getCookie(): Cookie = Cookie("auth", "${LocalDateTime.now()}")
    private fun getFakeCookie(): Cookie = Cookie("none", "${LocalDateTime.now()}")

    @Test
    fun `test get list`() {
        mock.perform(get("/app/list").cookie(getCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
    }

    @Test
    fun `test get list with parameter`() {
        mock.perform(get("/app/list").cookie(getCookie()).param("name", "Billy").param("phone", "7-924-202-35-02"))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
            .andExpect(content().string(containsString("Billy")))
            .andExpect(content().string(containsString("7-924-202-35-02")))
    }

    @Test
    fun `test get view`() {
        mock.perform(get("/app/0/view").cookie(getCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("view"))
    }

    @Test
    fun `test add get`() {
        mock.perform(get("/app/add").cookie(getCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("add"))
    }

    @Test
    fun `test add post`() {
        mock.perform(post("/app/add").cookie(getCookie())
            .param("name", "Jimmy")
            .param("phone", "7-956-489-51-62"))
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))
    }

    @Test
    fun `test edit get`() {
        mock.perform(get("/app/0/edit").cookie(getCookie()))
            .andExpect(status().isOk)
            .andExpect(view().name("edit"))
    }

    @Test
    fun `test edit post`() {
        mock.perform(post("/app/1/edit").cookie(getCookie())
            .param("name", "Jimmy")
            .param("phone", "7-956-489-51-62"))
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))

        mock.perform(get("/app/list")).andExpect(status().isOk)
            .andExpect(content().string(containsString("Jimmy")))
    }

    @Test
    fun `test delete`() {
        mock.perform(get("/app/2/delete").cookie(getCookie()))
            .andExpect(status().is3xxRedirection)

        mock.perform(get("/app/list")).andExpect(status().isOk)
            .andExpect(content().string( not(containsString("Israel")) ))
    }

}