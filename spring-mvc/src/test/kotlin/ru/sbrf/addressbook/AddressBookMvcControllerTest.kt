package ru.sbrf.addressbook

import org.apache.commons.lang3.StringUtils
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(PER_CLASS)
internal class AddressBookMvcControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun addEmployeeGetTest() {
        mockMvc.perform(get("/app/add"))
            .andExpect(status().isOk)
            .andExpect(view().name("add"))
    }


    @Test
    fun addEmployeePostTest() {
        addValidEmployee()
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))
    }

    @Test
    fun addEmployeePostTestInvalid() {
        addInvalidEmployee()
            .andExpect(view().name("add"))
    }


    @Test
    fun listEmployeesGetAll() {
        addValidEmployee()
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
            .andExpect(content().string(containsString("Имян")))
    }

    @Test
    fun listEmployeesGetByQuery() {
        addValidEmployee()
        addSpecificEmployee("Александр", "Пушкин")
        mockMvc.perform(get("/app/list").param("lastname", "Пушкин"))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
            .andExpect(content().string(containsString("Пушкин")))
            .andExpect(content().string(not(containsString("Имян"))))
    }

    @Test
    fun viewEmployee() {
        addValidEmployee()
        val rs = mockMvc.perform(get("/app/list"))
            .andReturn()
            .response
            .getContentAsString()

        val id = StringUtils
            .substringBetween(rs, "    <tr>\n        <td>", "</td>")
            .toInt()

        mockMvc.perform(get("/app/${id}/view"))
            .andExpect(status().isOk)
    }

    @Test
    fun deleteEmployee() {
        addValidEmployee()
        mockMvc.perform(get("/app/1/delete"))
            .andExpect(status().is3xxRedirection)
            .andExpect(view().name("redirect:/app/list"))
    }

    @Test
    fun editCustomers() {
        addValidEmployee()
        mockMvc.perform(
            post("/app/1/edit")
                .param("firstName", "ИмянEdit")
                .param("lastName", "ФамильевичEdit")
        ).andExpect(status().is3xxRedirection)
        .andExpect(view().name("redirect:/app/list"))

        mockMvc.perform(
            get("/app/list")
        ).andExpect(status().isOk)
        .andExpect(content().string(containsString("ИмянEdit")))
    }


    private fun addSpecificEmployee(name: String, lastName: String): ResultActions =
        mockMvc.perform(
            post("/app/add")
                .param("firstName", name)
                .param("lastName", lastName)
                .param("patronymic", "Отчествич")
                .param("address", "Ул. Птушкина, д. Колотушкина")
                .param("phone", "стационарный")
                .param("email", "test@email.com")
        )


    private fun addValidEmployee(): ResultActions =
        mockMvc.perform(
            post("/app/add")
                .param("firstName", "Имян")
                .param("lastName", "Фамильевич")
                .param("patronymic", "Отчествич")
                .param("address", "Ул. Птушкина, д. Колотушкина")
                .param("phone", "стационарный")
                .param("email", "test@email.com")
        )

    private fun addInvalidEmployee(): ResultActions =
        mockMvc.perform(
            post("/app/add")
                .param("firstName", "")
                .param("lastName", "")
                .param("patronymic", "")
                .param("address", "Ул. , д. ")
                .param("phone", "стационарный")
                .param("email", "   ")
        )

}