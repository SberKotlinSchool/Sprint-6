package ru.sber.app

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.sber.app.controllers.AppController


@SpringBootTest
@AutoConfigureMockMvc
class MvcMockApplicationTests {
	@Autowired
	private lateinit var mockMvc : MockMvc
	@Autowired
	private lateinit var appController: AppController

	@Test
	fun contextLoads() {
		assertThat(appController).isNotNull
	}

	@Test
	fun testAuth() {
		mockMvc.perform(post("/auth"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("authorization"))
			.andExpect(content().string(containsString("Вы ввели правильную пару логин/пароль. Нажмите, чтобы продолжить")))
			.andExpect(content().string(containsString("Продолжить")))
	}

	@Test
	fun testAppPost() {
		mockMvc.perform(post("/app"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("main_page"))
			.andExpect(content().string(containsString("Перейдите в интересующий Вас пункт меню:")))
			.andExpect(content().string(containsString("Добавление записи")))
			.andExpect(content().string(containsString("Просмотр и поиск записей")))
			.andExpect(content().string(containsString("Воспользоваться API")))
			.andExpect(content().string(containsString("logout")))
	}

	@Test
	fun testAppGet() {
		mockMvc.perform(get("/app"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("main_page"))
			.andExpect(content().string(containsString("Перейдите в интересующий Вас пункт меню:")))
			.andExpect(content().string(containsString("Добавление записи")))
			.andExpect(content().string(containsString("Просмотр и поиск записей")))
			.andExpect(content().string(containsString("Воспользоваться API")))
			.andExpect(content().string(containsString("logout")))
	}

	@Test
	fun testAppAdd() {
		mockMvc.perform(post("/app/add")
			.param("firstName", "Петр")
			.param("lastName", "Петров")
			.param("city", "Петровск"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("addition"))
			.andExpect(content().string(containsString("Заполните поля и нажмите кнопку \"Записать\"")))
			.andExpect(content().string(containsString("Записать")))
			.andExpect(content().string(containsString("Назад")))
	}

	@Test
	fun testAppListPost() {
		mockMvc.perform(post("/app/list").param("id", "1")
			.param("data", "1", "Петр", "Петров", "Петровск"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("book"))
			.andExpect(model().attributeExists("data"))
			.andExpect(content().string(containsString("Адресная книга")))
			.andExpect(content().string(containsString("Поиск по уникальному ID")))
			.andExpect(content().string(containsString("Найти")))
			.andExpect(content().string(containsString("ID")))
			.andExpect(content().string(containsString("Имя")))
			.andExpect(content().string(containsString("Фамилия")))
			.andExpect(content().string(containsString("Город проживания")))
			.andExpect(content().string(containsString("Назад")))
	}

	@Test
	fun testAppListGet() {
		mockMvc.perform(get("/app/list").param("id", "1")
			.param("data", "1","Петр", "Петров", "Петровск"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("book"))
			.andExpect(model().attributeExists("data"))
			.andExpect(content().string(containsString("Адресная книга")))
			.andExpect(content().string(containsString("Поиск по уникальному ID")))
			.andExpect(content().string(containsString("Найти")))
			.andExpect(content().string(containsString("ID")))
			.andExpect(content().string(containsString("Имя")))
			.andExpect(content().string(containsString("Фамилия")))
			.andExpect(content().string(containsString("Город проживания")))
			.andExpect(content().string(containsString("Назад")))
	}

	@Test
	fun testAppListSettingPost() {
		mockMvc.perform(post("/app/field_setting").param("id", "1"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("setting_menu"))
			.andExpect(model().attributeExists("id"))
			.andExpect(content().string(containsString("Просмотр записи")))
			.andExpect(content().string(containsString("Редактирование записи")))
			.andExpect(content().string(containsString("Удалить запись")))
			.andExpect(content().string(containsString("К Списку")))
	}

	@Test
	fun testAppListSettingGet() {
		mockMvc.perform(get("/app/field_setting"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("setting_menu"))
			.andExpect(model().attributeExists("id"))
			.andExpect(content().string(containsString("Просмотр записи")))
			.andExpect(content().string(containsString("Редактирование записи")))
			.andExpect(content().string(containsString("Удалить запись")))
			.andExpect(content().string(containsString("К Списку")))
	}

	@Test
	fun testViewApp() {
		mockMvc.perform(post("/app/1/view").param("id", "1")
			.param("data", "Петр", "Петров", "Петровск"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("field"))
			.andExpect(model().attributeExists("id", "data"))
			.andExpect(content().string(containsString("Уникальный ID: ")))
			.andExpect(content().string(containsString("1")))
			.andExpect(content().string(containsString("Имя")))
			.andExpect(content().string(containsString("Фамилия")))
			.andExpect(content().string(containsString("Город проживания")))
			.andExpect(content().string(containsString("Назад")))
	}

	@Test
	fun testEditApp() {
		mockMvc.perform(post("/app/1/edit").param("id", "1")
			.param("firstName", "Петр")
			.param("lastName", "Петров")
			.param("city", "Петровск"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("editor"))
			.andExpect(model().attributeExists("id"))
			.andExpect(content().string(containsString("Запись с ID: ")))
			.andExpect(content().string(containsString("1")))
			.andExpect(content().string(containsString("Внесите изменения в соответствующие поля и подтвердите:")))
			.andExpect(content().string(containsString("Подтверждаю изменения")))
			.andExpect(content().string(containsString("К списку")))
	}

	@Test
	fun testDeleteApp() {
		mockMvc.perform(post("/app/1/delete").param("id", "1"))
			.andDo(print())
			.andExpect(status().isOk)
			.andExpect(view().name("del"))
			.andExpect(model().attributeExists("id"))
			.andExpect(content().string(containsString("Запись с ID: ")))
			.andExpect(content().string(containsString("1")))
			.andExpect(content().string(containsString(" удалена.")))
			.andExpect(content().string(containsString("К списку")))
	}
}
