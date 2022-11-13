package ru.sber

import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest
class AddressBookMVCControllerTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@BeforeEach
	fun setUp() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/app/add")
				.param("fio", "Мясников Иван")
				.param("address", "Морская")
				.param("phone", "+79219314455")
				.param("email", "1141@gmail.com")
		)
	}

	@Test
	fun addPersonFormTest() {

		mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.view().name("add"))

	}

	@Test
	fun addPersonTest() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/app/add")
				.param("fio", "ФИО")
				.param("address", "ул. Кирова")
				.param("phone", "+7")
				.param("email", "email@gmail.com")
		)
			.andExpect(MockMvcResultMatchers.status().`is`(302))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
	}

	@Test
	fun getListWithoutParamTest() {
		mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.view().name("list"))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Мясников Иван")))
	}

	@Test
	fun getListWithParamTest() {
		mockMvc.perform(
			MockMvcRequestBuilders.get("/app/list")
				.param("phone", "+79219314455")
		)
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.view().name("list"))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Мясников Иван")))
	}

	@Test
	fun viewTest() {
		mockMvc.perform(MockMvcRequestBuilders.get("/app/1/view"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.view().name("view"))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("+79219314455")))
	}

	@Test
	fun deleteTest() {
		mockMvc.perform(MockMvcRequestBuilders.get("/app/1/delete"))
			.andExpect(MockMvcResultMatchers.status().`is`(302))
	}

	@Test
	fun editTest() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/app/1/edit")
				.param("fio", "Фамилия")
				.param("address", "Адрес")
				.param("phone", "911")
				.param("email", "email1@gmail.com")
		)
			.andExpect(MockMvcResultMatchers.status().`is`(302))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/app/list"))
	}
}
