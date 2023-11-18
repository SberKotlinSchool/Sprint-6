package ru.sber.repository

import org.junit.jupiter.api.Test
import ru.sber.model.Note
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class AddressBookRepositoryImplTest {

    private val repository = AddressBookRepositoryImpl()

    @Test
    fun createAndGetById() {
        //given
        val note = Note("name1", "address1", "phone1")
        repository.create(note)
        //when
        val result = repository.getById(0)
        //then
        assertEquals(note, result)
    }

    @Test
    fun getAll() {
        //given
        val note1 = Note("name1", "address1", "phone1")
        val note2 = Note("name2", "address2", "phone2")
        repository.create(note1)
        repository.create(note2)
        //when
        val result = repository.getAll()
        //then
        assertContentEquals(listOf(note1, note2), result)
    }

    @Test
    fun searchByName() {
        //given
        val note1 = Note("name1", "address1", "phone1")
        val note2 = Note("name2", "address2", "phone2")
        repository.create(note1)
        repository.create(note2)
        //when
        val result = repository.searchByName("name2")
        //then
        assertEquals(listOf(note2), result)
    }

    @Test
    fun searchByAddress() {
        //given
        val note1 = Note("name1", "address", "phone1")
        val note2 = Note("name2", "address", "phone2")
        repository.create(note1)
        repository.create(note2)
        //when
        val result = repository.searchByAddress("address")
        //then
        assertEquals(listOf(note1, note2), result)
    }

    @Test
    fun searchByPhone() {
        //given
        val note1 = Note("name1", "address", "phone1")
        val note2 = Note("name2", "address", "phone2")
        repository.create(note1)
        repository.create(note2)
        //when
        val result = repository.searchByAddress("phone3")
        //then
        assertEquals(listOf(), result)
    }

    @Test
    fun deleteById() {
        //given
        val note1 = Note("name1", "address1", "phone1")
        val note2 = Note("name2", "address2", "phone2")
        repository.create(note1)
        repository.create(note2)
        //when
        repository.deleteById(0L)
        //then
        val result = repository.getAll()
        assertContentEquals(listOf(note2), result)
    }

    @Test
    fun updateById() {
        //given
        val note1 = Note("name1", "address1", "phone1")
        repository.create(note1)
        //when
        val note2 = Note("name2", "address2", "phone2")
        repository.updateById(0L, note2)
        //then
        val result = repository.getById(0L)
        assertEquals(note2, result)
    }
}