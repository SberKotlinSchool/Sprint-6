package ru.sber.addressbook

import junit.framework.TestCase.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import ru.sber.addressbook.dto.AddressModel
import ru.sber.addressbook.dto.ResponseDTO
import ru.sber.addressbook.repository.AddressRepository
import java.time.LocalDate

@RunWith(SpringJUnit4ClassRunner::class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressControllerApiTest {

  @LocalServerPort
  private var port: Int = 0

  @Autowired
  private lateinit var restTemplate: TestRestTemplate

  @Autowired
  private lateinit var repository: AddressRepository

  private val cookieHeader = HttpHeaders().apply {
    set("Cookie", "session=${LocalDate.now().atStartOfDay()}")
  }

  private fun url(s: String) = "http://localhost:${port}/${s}"

  @Test
  fun addAddress() {
    val address = AddressModel( "Иванов Иван Иванович",  "Улица Пушкина, 2", 1)

    val result = restTemplate.exchange(
        url("/api/add"),
        HttpMethod.POST,
        HttpEntity(address, cookieHeader),
        object : ParameterizedTypeReference<ResponseDTO<AddressModel>>() {}
    )

    assertTrue(result.statusCode.is2xxSuccessful)
    assertEquals(address, result.body?.data)
  }

  @Test
  fun viewListOfAddresses() {
    // given
    val address = AddressModel("Дима Дима Дима", "Муром").let {
      repository.saveAddress(it)
    }

    // when
    val result = restTemplate.exchange(
        url("/api/list"),
        HttpMethod.GET,
        HttpEntity<Any>(cookieHeader),
        object : ParameterizedTypeReference<ResponseDTO<List<AddressModel>>>() {}
    )

    // then
    assertTrue(result.statusCode.is2xxSuccessful)
    assertTrue(result.body?.data?.isNotEmpty() == true)
    assertEquals(address, result.body?.data?.first())
  }

  @Test
  fun viewAddress() {
    // given
    val address = AddressModel("Дима Дима Дима", "Муром 412351").let {
      repository.saveAddress(it)
    }

    // when
    val result = restTemplate.exchange(
        url("/api/${address?.id}"),
        HttpMethod.GET,
        HttpEntity<Any>(cookieHeader),
        object : ParameterizedTypeReference<ResponseDTO<AddressModel>>() {}
    )

    // then
    assertTrue(result.statusCode.is2xxSuccessful)
    assertEquals(address, result.body?.data)
  }

  @Test
  fun editAddress() {
    // given
    val address = repository.saveAddress(AddressModel("Дима Дима Дима", "Муром 123"))!!
    val modifiedAddress = address.copy(
        name = "Вася Вася Вася"
    )

    // when
    val result = restTemplate.exchange(
        url("/api/${address.id}/update"),
        HttpMethod.PUT,
        HttpEntity(modifiedAddress, cookieHeader),
        object : ParameterizedTypeReference<ResponseDTO<AddressModel>>() {}
    )

    // then
    assertTrue(result.statusCode.is2xxSuccessful)
    println(result.body?.data)
    assertEquals(modifiedAddress, result.body?.data)
  }

  @Test
  fun deleteAddress() {
    // given
    val address = AddressModel("Дима Дима Дима", "Муром 12414").let {
      repository.saveAddress(it)
    }

    // when
    val result = restTemplate.exchange(
        url("/api/${address?.id}/delete"),
        HttpMethod.DELETE,
        HttpEntity<Any>(cookieHeader),
        object : ParameterizedTypeReference<ResponseDTO<List<AddressModel>>>() {}
    )

    // then
    assertTrue(result.statusCode.is2xxSuccessful)
    assertNull(repository.getAddressById(address!!.id))
  }
}