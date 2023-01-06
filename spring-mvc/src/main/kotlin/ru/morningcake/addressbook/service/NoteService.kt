package ru.morningcake.addressbook.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.morningcake.addressbook.dao.NoteRepository
import ru.morningcake.addressbook.dao.UserRepository
import ru.morningcake.addressbook.dto.NoteDto
import ru.morningcake.addressbook.entity.Note
import ru.morningcake.addressbook.entity.User
import ru.morningcake.addressbook.exception.EntityNotFoundException
import java.util.*

@Service
class NoteService @Autowired constructor (private val repository : NoteRepository) {

    fun getById(id : UUID) : Note {
        return repository.getById(id)?: throw EntityNotFoundException("Запись $id не найдена!")
    }

    fun getAll() : List<Note> {
        return repository.getAll()
    }

    fun search(filter : String) : List<Note> {
        return repository.search(filter)
    }

    fun create(dto : NoteDto) : Note {
        return repository.create(dto)
    }

    fun update(id : UUID, dto : NoteDto) : Note {
        getById(id)
        return repository.update(id, dto)
    }

    fun delete(id : UUID) {
        getById(id)
        repository.delete(id)
    }
}