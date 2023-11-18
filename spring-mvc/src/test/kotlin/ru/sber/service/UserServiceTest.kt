package ru.sber.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import ru.sber.model.User
import ru.sber.repository.UserRepository
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class UserServiceTest {
    private var repository: UserRepository = mockk()
    private val userService = UserService(repository)

    @Test
    fun isUserCorrectTrue() {
        //given
        val user = User("login", "password")
        every { repository.getByLogin(any()) }.returns(user)
        //when
        //then
        assertTrue(userService.isUserCorrect(user))
    }

    @Test
    fun isUserCorrectFalse() {
        //given
        val user = User("login", "password")
        every { repository.getByLogin(any()) }.returns(user)
        val user2 = User("login2", "password2")
        //when
        //then
        assertFalse(userService.isUserCorrect(user2))
    }
}