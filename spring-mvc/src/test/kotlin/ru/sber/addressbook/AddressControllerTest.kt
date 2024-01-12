package ru.sber.adressbook.controller

import jakarta.servlet.http.Cookie
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import ru.sber.addressbook.dto.AddressModel
import ru.sber.addressbook.repository.AddressRepository
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressControllerTest {

  @Autowired
  private lateinit var repository: AddressRepository

  @Autowired
  private lateinit var mockMvc: MockMvc

  private val cookie = Cookie("session", LocalDate.now().atStartOfDay().toString())

  @Test
  fun viewAddress() {
    val address = repository.saveAddress(AddressModel("Дима Дима Дима", "Муром"))!!

    mockMvc.get("/app/${address.id}") {
      cookie(cookie)
    }
        .andDo { print() }
        .andExpect {
          status { is2xxSuccessful() }
          model { attributeExists("address") }
          model { attribute("address", Matchers.equalTo(address)) }
        }
  }

  @Test
  fun viewListOfAddresses() {
    mockMvc.get("/app/list") {
      cookie(cookie)
    }
        .andDo { print() }
        .andExpect {
          status { is2xxSuccessful() }
          model { attributeExists("addresses") }
        }
  }



  @Test
  fun editAddress() {
    val address = repository.saveAddress(AddressModel("Дима Дима Дима", "Муром"))!!
    val modifiedAddress = address.copy(
        name = "Петров Петр Петрович"
    )

    mockMvc.put("/app/${address.id}/edit") {
      cookie(cookie)
      param("name", modifiedAddress.name)
      param("address", modifiedAddress.address)
    }
        .andDo { print() }
        .andExpect {
          status { is3xxRedirection() }
          redirectedUrl("/app/list")
        }
        .andReturn()
    assertEquals(repository.getAddressById(address.id), modifiedAddress)
  }

  @Test
  fun addAddress() {
    val address = AddressModel("Дима Дима Дима", "Муром")

    mockMvc.post("/app/add") {
      cookie(cookie)
      param("name", address.name)
      param("address", address.address)
    }
        .andDo { print() }
        .andExpect {
          status { is3xxRedirection() }
          redirectedUrl("/app/list")
        }
  }
  @Test
  fun deleteAddress() {
    val address = repository.saveAddress(AddressModel( "Дима Дима Дима", "Муром"))!!

    mockMvc.delete("/app/${address.id}/delete") {
      cookie(cookie)
    }
        .andDo { print() }
        .andExpect {
          status { is3xxRedirection() }
          redirectedUrl("/app/list")
        }
    assertNull(repository.getAddressById(address.id))
  }
}