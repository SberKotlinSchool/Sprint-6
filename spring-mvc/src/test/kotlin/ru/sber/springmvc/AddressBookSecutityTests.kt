package ru.sber.springmvc

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
import org.springframework.transaction.annotation.Transactional
import ru.sber.springmvc.domain.Record
import ru.sber.springmvc.service.AddressBookService
import ru.sber.springmvc.service.UserSecurityService

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressBookSecutityTests {

    @Autowired
    lateinit var recordService: AddressBookService

    @Autowired
    lateinit var userService: UserSecurityService

    @Test
    @WithMockUser(username = "user")
    fun userShouldGetAllRecords() {
        val records: List<Record> = recordService.getAll()
        Assertions.assertNotNull(records)
        Assertions.assertEquals(2, records.size)
    }

    @Test
    @WithMockUser(username = "admin")
    fun adminShouldGetAllRecords() {
        val records: List<Record> = recordService.getAll()
        Assertions.assertNotNull(records)
        Assertions.assertEquals(2, records.size)
    }

    @Test
    @Transactional
    @WithMockUser(authorities = ["ROLE_ADMIN"])
    fun adminShouldCreateRecord() {
        userService.add(Record(5L, "Ulala", "Nanana"))
        val records: List<Record> = recordService.getAll()
        Assertions.assertNotNull(records)
        Assertions.assertEquals(3, records.size)
    }

    @Test
    @WithMockUser(username = "user")
    fun userShouldNotCreateRecord() {
        val exception: Exception = Assertions.assertThrows(
            AccessDeniedException::class.java
        ) {
            recordService.insert(
                Record(
                    4L,
                    "Lalala",
                    "Lalalalala"
                )
            )
        }
        val expectedMessage = "Access is denied"
        val actualMessage = exception.message
        Assertions.assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    @WithMockUser(username = "user")
    fun userShouldNotDeleteRecord() {
        val exception: Exception = Assertions.assertThrows(
            AccessDeniedException::class.java
        ) { recordService.deleteById(1) }
        val expectedMessage = "Access is denied"
        val actualMessage = exception.message
        Assertions.assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    @WithMockUser(authorities = ["ROLE_ADMIN"])
    fun adminShouldDeleteRecord() {
        val exception: Exception = Assertions.assertThrows(
            NoSuchElementException::class.java
        ) {
            recordService.insert(Record(4L, "Trarara", "Tratata"))
            recordService.deleteById(4L)
            recordService.getById(4L)
        }
        val expectedMessage = "Key 4 is missing in the map"
        val actualMessage = exception.message
        Assertions.assertTrue(actualMessage!!.contains(expectedMessage))
    }
}