package ru.sber.app

import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.sber.model.record.RecordDAO
import ru.sber.model.record.RecordDTO
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.Cookie

@SpringBootTest
@AutoConfigureMockMvc
class MvcControllerTest {

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var repository: RecordDAO

  @BeforeEach
  fun setUp() {
    repository.clear()
  }

  fun initRepository(name: String){
    repository.setUp(
      ConcurrentHashMap(mapOf(
        1 to RecordDTO(1, "1", name, "lastName", "secondName", "phoneNumber",
          RecordDTO.Address("city", "street", 1, 123456)))))
  }

  @Test
  fun getList() {
    val name = "testAppList"
    initRepository(name)
    mockMvc.perform(get("/app/list")
      .cookie(Cookie("auth", "userID=1&datetime=${LocalDateTime.now()}")))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk())
      .andExpect(view().name("main"))
      .andExpect(content().string(containsString(name)))
  }

  @Test
  fun getSearchList() {
    val name = "testAppSearchList"
    initRepository(name)
    mockMvc.perform(get("/app/search")
      .param("query", "123456")
      .cookie(Cookie("auth", "userID=1&datetime=${LocalDateTime.now()}")))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk())
      .andExpect(view().name("main"))
      .andExpect(content().string(containsString(name)))
  }

  @Test
  fun addRecord() {
    mockMvc.perform(post("/app/add")
      .flashAttr("recordDTO", RecordDTO(name = "testAddRecord"))
      .cookie(Cookie("auth", "userID=1&datetime=${LocalDateTime.now()}")))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(redirectedUrl("/app/list"))

    mockMvc.perform(get("/app/list")
      .cookie(Cookie("auth", "userID=1&datetime=${LocalDateTime.now()}")))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk())
      .andExpect(view().name("main"))
      .andExpect(content().string(containsString("testAddRecord")))
  }

  @Test
  fun viewRecord() {
    val name = "testAppView"
    initRepository(name)
    mockMvc.perform(get("/app/1/view")
      .cookie(Cookie("auth", "userID=1&datetime=${LocalDateTime.now()}")))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk())
      .andExpect(view().name("view"))
      .andExpect(content().string(containsString(name)))
  }

  @Test
  fun editRecord() {
    val name = "testAppEdit"
    initRepository(name)
    mockMvc.perform(post("/app/1/edit")
      .flashAttr("recordDTO", RecordDTO(name = "testEditRecord"))
      .cookie(Cookie("auth", "userID=1&datetime=${LocalDateTime.now()}")))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(redirectedUrl("/app/1/view"))

    mockMvc.perform(get("/app/1/view")
      .cookie(Cookie("auth", "userID=1&datetime=${LocalDateTime.now()}")))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk())
      .andExpect(view().name("view"))
      .andExpect(content().string(containsString("testEditRecord")))
  }

  @Test
  fun deleteRecord() {
    val name = "testAppDelete"
    initRepository(name)
    mockMvc.perform(post("/app/1/delete")
      .cookie(Cookie("auth", "userID=1&datetime=${LocalDateTime.now()}")))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(redirectedUrl("/app/list"))

    mockMvc.perform(get("/app/list")
      .cookie(Cookie("auth", "userID=1&datetime=${LocalDateTime.now()}")))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk())
      .andExpect(view().name("main"))
      .andExpect(content().string(not(containsString(name))))
  }
}