package io.vorotov.diary

import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest
@AutoConfigureMockMvc
class DiaryMvcControllerTest {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@BeforeEach
	fun setUp() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/app/add")
				.param("date", "2011-11-01")
				.param("message", "1111111111111111111111")
		)
	}

	@Test
	fun `add record test`() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/app/add")
				.param("date", "2022-12-22")
				.param("message", "2222222222222")
		)
			.andExpect(MockMvcResultMatchers.status().`is`(302))
	}

	@Test
	fun `list recor test`() {
		mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.view().name("list"))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1111111111111111111111")))
	}
	@Test
	fun `list two records test`() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/app/add")
				.param("date", "2222-02-22")
				.param("message", "2222222222")
		)
		mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.view().name("list"))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1111111111111111111111")))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("2222222222")))
	}

	@Test
	fun `view record test`() {
		mockMvc.perform(MockMvcRequestBuilders.get("/app/2011-11-01/view"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.view().name("view"))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("2011-11-01")))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1111111111111111111111")))
	}


	@Test
	fun `delete clients test`() {
		mockMvc.perform(MockMvcRequestBuilders.post("/app/2011-11-01/delete"))
			.andExpect(MockMvcResultMatchers.status().`is`(302))
	}

	@Test
	fun `edit record test`() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/app/2011-11-01/edit")
				.param("date", "2011-11-11")
				.param("message", "121212121212")
		)
			.andExpect(MockMvcResultMatchers.status().`is`(302))

		mockMvc.perform(MockMvcRequestBuilders.get("/app/2011-11-11/view"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.view().name("view"))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("2011-11-11")))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("121212121212")))
	}

}
