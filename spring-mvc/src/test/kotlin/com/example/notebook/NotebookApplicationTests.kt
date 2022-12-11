package com.example.notebook

import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest
class NotebookApplicationTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Test
	fun addNoteTest() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/note/new")
				.param("login", "starikovamari")
					.param("pass", "")
				.param("note", "some note")
		).andExpect(MockMvcResultMatchers.status().`is`(200))
			.andExpect(MockMvcResultMatchers.content().string(containsString("some note")))

	}
	@Test
	fun deleteNoteTest() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/note/delete")
				.param("id","1")
				.param("login", "starikovamari")
				.param("pass", "")
		).andExpect(MockMvcResultMatchers.status().`is`(200))
			.andExpect(MockMvcResultMatchers.content().string(containsString("Notebook")))
	}

	@Test
	fun editNoteTest() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/note/edit")
				.param("id","1")
				.param("note", "sixth note")
				.param("login", "starikovamari")
				.param("pass", "")
		).andExpect(MockMvcResultMatchers.status().`is`(200))
			.andExpect(MockMvcResultMatchers.content().string(containsString("sixth note")))
	}

}
