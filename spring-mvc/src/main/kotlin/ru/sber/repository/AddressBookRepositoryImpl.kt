package ru.sber.repository

import org.springframework.stereotype.Repository
import ru.sber.model.Note
import java.util.concurrent.ConcurrentHashMap

@Repository
class AddressBookRepositoryImpl : AddressBookRepository {
    private var notes = ConcurrentHashMap<Long, Note>()
    private var currentId = 0L

    override fun getById(id: Long): Note? {
        return notes[id]
    }

    override fun getAll(): List<Note> {
        return notes
            .map { it.value }
            .toList()
    }

    override fun searchWithFilter(name: String?, address: String?, phone: String?): List<Note> {
        return notes
            .map { it.value }
            .filter { if (name == null) true else name == it.name }
            .filter { if (address == null) true else address == it.address }
            .filter { if (phone == null) true else phone == it.phone }
            .toList()
    }

    override fun searchByName(name: String): List<Note> {
        return notes
            .filter { name == it.value.name }
            .map { it.value }
            .toList()
    }

    override fun searchByAddress(address: String): List<Note> {
        return notes
            .filter { address == it.value.address }
            .map { it.value }
            .toList()
    }

    override fun searchByPhone(phone: String): List<Note> {
        return notes
            .filter { phone == it.value.phone }
            .map { it.value }
            .toList()
    }

    override fun create(note: Note) {
        notes[currentId++] = note
    }

    override fun deleteById(id: Long) {
        notes.remove(id)
    }

    override fun updateById(id: Long, note: Note) {
        notes[id] = note
    }

    override fun deleteAll() {
        notes.clear()
        currentId = 0
    }
}
