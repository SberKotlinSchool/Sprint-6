package ru.sber.springmvc.dto

import ru.sber.springmvc.utils.RequestUtils.Companion.DAY_MONTH_HMS
import ru.sber.springmvc.utils.RequestUtils.Companion.notes
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Note (var author: String,
                 var content: String,
                 var date: String) {

    override fun toString(): String {
//        return "Note(author='$author', content='$content', date=$date)"
        return "${date}\t$author:\t$content"
    }
    companion object {
        fun saveNote(note: Note) {
            note.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DAY_MONTH_HMS))
            notes.add(note)
        }

        fun deleteNote(id: Int) {
            if (notes.size > id) notes.removeAt(id)
        }

        fun editNote(id: Int, note:Note) {
            if (notes.size > id) {
                note.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DAY_MONTH_HMS))
                notes[id] = note
            }
        }
    }
}