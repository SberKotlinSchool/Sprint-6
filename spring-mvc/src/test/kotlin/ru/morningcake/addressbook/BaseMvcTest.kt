package ru.morningcake.addressbook

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
abstract class BaseMvcTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    val objectMapper: ObjectMapper = jacksonObjectMapper()
        .registerModules(JavaTimeModule(), Jdk8Module())
        .enable(SerializationFeature.INDENT_OUTPUT)

    final inline fun <reified T> readDtoFromJson(json : String) : T {
        return objectMapper.readValue(json)
    }

    fun <T> writeJson(dto : T) : String {
        return objectMapper.writeValueAsString(dto)
    }
}