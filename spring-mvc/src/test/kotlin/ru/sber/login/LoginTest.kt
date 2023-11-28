package ru.sber.login

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import ru.sber.model.user.UserDTO
import ru.sber.model.user.UserService

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginTest {

  @LocalServerPort
  private var port: Int = 80

  @Autowired
  private lateinit var restTemplate: TestRestTemplate

  private val service: UserService = mockk()

  private fun url(path: String): String {
    return "http://localhost:${port}/${path}"
  }

  @BeforeEach
  fun setUp() {
    restTemplate = TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES,
      TestRestTemplate.HttpClientOption.ENABLE_REDIRECTS)
  }

  @Test
  fun authSuccessfully() {
    val user = UserDTO("1", "user1", "pass1")

    every { service.getUser(any(), any()) } returns user

    val requestParams = LinkedMultiValueMap<String, String>()
    requestParams.add("username", user.username)
    requestParams.add("password", user.password)

    val request = HttpEntity<MultiValueMap<String, String>>(requestParams, HttpHeaders())

    val resp = restTemplate.exchange(
      url("app/login"),
      HttpMethod.POST,
      request,
      String::class.java)

    println(resp.headers)

    assertTrue(resp.headers.location!!.path.contains("app/list"))
    assertTrue(resp.headers.containsKey("Set-Cookie"))
    assertTrue(resp.headers["Set-Cookie"]!![0].contains("userID=1"))
  }

  @Test
  fun authFailure() {
    every { service.getUser(any(), any()) } returns null

    val requestParams = LinkedMultiValueMap<String, String>()
    requestParams.add("username", "username")
    requestParams.add("password", "password")

    val request = HttpEntity<MultiValueMap<String, String>>(requestParams, HttpHeaders())

    val resp = restTemplate.exchange(
      url("app/login"),
      HttpMethod.POST,
      request,
      String::class.java)

    assertFalse(resp.headers["Set-Cookie"]!![0].contains("auth"))
  }
}