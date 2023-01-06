package ru.morningcake.addressbook.dao

import org.springframework.stereotype.Repository
import ru.morningcake.addressbook.dto.NoteDto
import ru.morningcake.addressbook.dto.mapper.noteFromDto
import ru.morningcake.addressbook.entity.Note
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Repository
class NoteRepository {

    private var notes = ConcurrentHashMap<UUID, Note>()

    fun count() : Int {
        return notes.size
    }

    fun getById(id : UUID) : Note? {
        return notes.getOrDefault(id, null)
    }

    fun getAll() : List<Note> {
        return notes.values.toList().sortedBy { it.name }
    }

    fun search(filter : String) : List<Note> {
        return getAll()
            .filter { it.name.lowercase().contains(filter.lowercase()) ||
                    it.phone.lowercase().contains(filter.lowercase()) ||
                    it.address.lowercase().contains(filter.lowercase())
            }.sortedBy { it.name }
    }

    fun create(dto : NoteDto) : Note {
        val id = UUID.randomUUID()
        val note = noteFromDto(id, dto)
        notes[id] = note
        return note
    }

    fun update(id : UUID, dto : NoteDto) : Note {
        val note = noteFromDto(id, dto)
        notes.replace(id, note)
        return note
    }

    fun delete(id : UUID) {
        notes.remove(id)
    }

    fun clear() {
        notes.clear()
    }
}