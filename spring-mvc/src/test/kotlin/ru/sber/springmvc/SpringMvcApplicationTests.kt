package ru.sber.springmvc

import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap

@SpringBootTest
@AutoConfigureMockMvc
class SpringMvcApplicationTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Test
	fun testIndex() {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk)
			.andExpect(content().string(containsString("What do you want?")))
	}

	@Test
	fun testLogin() {
		mockMvc.perform(get("/api/login?name=user&pass=user"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.status").value("fail"))
	}

	@Test
	fun testRegister() {
		val map = LinkedMultiValueMap<String, String>()
		map.add("userName", "test")
		map.add("password", "test")

		mockMvc.perform(post("/register").params(map))
			.andExpect(status().isOk)
			.andExpect(view().name("signIn"))
			.andExpect((content().string(containsString("Now enter your new credentials"))))
	}

}
