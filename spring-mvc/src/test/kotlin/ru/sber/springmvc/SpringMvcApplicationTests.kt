package ru.sber.springmvc

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SpringMvcApplicationTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Test
	fun testIndex() {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk)
	}

	@Test
	fun testLogin() {
		mockMvc.perform(get("/api/login?name=user&pass=user"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.status").value("fail"))
	}

}
