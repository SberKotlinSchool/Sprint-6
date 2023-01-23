package ru.sber.mvc

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import ru.sber.mvc.domain.DomainRecord.Companion.EMPTY


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AddressBookControllerTests {

    private companion object {
        const val DEFAULT_RECORD_ID = 1L
    }

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = ["ADMIN"])
    fun `test valid app add get request`() {
        mockMvc.perform(get("/app/add"))
            .andExpectAll(
                status().isOk,
                model().attribute("newRecord", EMPTY),
                view().name("add")
            )
    }

    @Test
    @WithMockUser(username = "user1", password = "user1", roles = ["USER"])
    fun `test valid app list get request`() {
        mockMvc.perform(get("/app/list"))
            .andExpectAll(
                status().isOk,
                model().attributeExists("records"),
                view().name("list")
            )
    }

    @Test
    @WithMockUser(username = "user2", password = "user2", roles = ["API"])
    fun `test invalid app list get request`() {
        mockMvc.perform(get("/app/list"))
            .andExpectAll(status().isForbidden)
    }

    @Test
    @WithMockUser(username = "user2")
    fun `test valid app view get request`() {
        mockMvc.perform(get("/app/${DEFAULT_RECORD_ID}/view"))
            .andExpectAll(
                status().isOk,
                view().name("view")
            )
    }

    @Test
    @WithMockUser(username = "user1", password = "user1")
    fun `test invalid delete post request`() {
        mockMvc.perform(post("/app/1/delete"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(status().isForbidden)
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    fun `test valid delete post request`() {
        mockMvc.perform(post("/app/1/delete"))
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                status().is3xxRedirection,
                redirectedUrl("/app/list")
            )
    }
}
