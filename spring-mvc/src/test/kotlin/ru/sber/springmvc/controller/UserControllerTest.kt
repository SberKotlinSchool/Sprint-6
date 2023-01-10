package ru.sber.springmvc.controller

import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.sber.springmvc.model.LoginDTO
import ru.sber.springmvc.model.User


@SpringBootTest
@AutoConfigureMockMvc
internal class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun userListViewTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(view().name("users"))
            .andExpect(content().string(containsString("Список пользователей")))
    }

    @Test
    fun userFindViewTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/search"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(view().name("find-user"))
            .andExpect(content().string(containsString("Найти пользователя")))
    }

    @Test
    fun userCreateViewTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/create"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(view().name("user"))
            .andExpect(content().string(containsString("Создать пользователя")))
    }

    @Test
    fun userEditViewTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/edit/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(view().name("user"))
            .andExpect(content().string(containsString("Изменить пользователя")))
    }

    @Test
    fun userSaveTest() {
        val user = createUser()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/user/save")
                .flashAttr("user", user)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/"))
    }

    @Test
    fun loginFormTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(view().name("login"))
            .andExpect(content().string(containsString("Перейти к списку пользователей")))
    }

    @Test
    fun loginOkTest() {
        val loginDTO = createSuccessfulLoginDTO()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/login")
                .flashAttr("loginDTO", loginDTO))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/"))
    }

    @Test
    fun loginFailedTest() {
        val loginDTO = createWrongLoginDTO()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/login")
                .flashAttr("loginDTO", loginDTO))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isUnauthorized)
            .andExpect(view().name("login"))
    }

    fun createUser(): User {
        return User(123L, "new", "pass", "password")
    }

    fun createSuccessfulLoginDTO() : LoginDTO = createLoginDTO("admin", "admin")
    fun createWrongLoginDTO() : LoginDTO = createLoginDTO("admin", "password")

    fun createLoginDTO(login: String, password: String) : LoginDTO {
        val loginDTO = LoginDTO()
        loginDTO.username = login
        loginDTO.password = password
        return loginDTO
    }
}