package ru.sber.AddressBook

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class AddressBookMVCApplicationTests {

	@Autowired
	private lateinit var mockMvc: MockMvc
	@Test
	fun `test should return list`() {
		mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.view().name("list"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("books"))
	}

	@Test
	fun `test find row in list`() {
		mockMvc.perform(
			MockMvcRequestBuilders
				.get("/app/list")
				.param("fio", "LastName7")
		)
			.andExpectAll(
				MockMvcResultMatchers.status().isOk,
				MockMvcResultMatchers.view().name("list"),
				MockMvcResultMatchers.content().string(Matchers.containsString("LastName7"))
			)
	}

	@Test
	fun `test get edit row`() {
		mockMvc.perform(
			MockMvcRequestBuilders
				.get("/app/1/edit")
		)
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.view().name("edit"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("customer"))
	}

	@Test
	fun `test send edited row`() {
		mockMvc.perform(
			MockMvcRequestBuilders
				.post("/app/1/edit")
				.param("lastName", "EditedLastName1")
				.param("firstName", "EditedFirstName1")
				.param("middleName", "EditedMiddleName1")
				.param("phone", "+79777777777")
				.param("address", "address1 new")
				.param("email", "edited email new")
		)
			.andExpectAll(
				MockMvcResultMatchers.status().is3xxRedirection,
				MockMvcResultMatchers.view().name("redirect:/app/list")
			)

		mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
			.andExpectAll(
				MockMvcResultMatchers.status().isOk,
				MockMvcResultMatchers.view().name("list"),
				MockMvcResultMatchers.content().string(
					Matchers.containsString("EditedLastName1")
				)
			)
	}

	@Test
	fun `delete user from list`() {
		mockMvc.perform(
			MockMvcRequestBuilders.get("/app/1/delete")
		)
			.andExpectAll(
				MockMvcResultMatchers.status().is3xxRedirection,
				MockMvcResultMatchers.view().name("redirect:/app/list"),
			)
	}
}
