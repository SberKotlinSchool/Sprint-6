package ru.sber.repository

import org.junit.jupiter.api.Test
import ru.sber.model.User
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class UserRepositoryImplTest {

    private val repository = UserRepositoryImpl()

    @Test
    fun createAndGetByLogin() {
        //given
        val user = User("login", "pass")
        repository.createUser(user)
        //when
        val result = repository.getByLogin("login")
        //then
        assertEquals(user, result)
    }

    @Test
    fun getByLoginNotExist() {
        //given
        //when
        val result = repository.getByLogin("login")
        //then
        assertNull(result)
    }

    @Test
    fun deleteByLogin() {
        //given
        val user = User("login", "pass")
        repository.createUser(user)
        //when
        repository.deleteByLogin("login")
        //then
        val result = repository.getByLogin("login")
        assertNull(result)
    }
}