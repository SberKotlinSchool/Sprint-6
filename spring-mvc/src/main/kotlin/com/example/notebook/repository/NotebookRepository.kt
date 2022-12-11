package com.example.notebook.repository

import org.springframework.stereotype.Repository

@Repository
class NotebookRepository {

    private var notebookDB = HashMap(mapOf("starikovamari" to mutableListOf("First note", "Second note")))

    fun getNotesByUser(userName: String) : List<String> {
        return when (notebookDB[userName]) {
            null -> EMPTY_LIST
            else -> notebookDB[userName]!!
        }
//        return if (notebookDB[userName] != null) notebookDB[userName]!! else listOf("Нет заметок")
    }

    fun addNote(userName: String, note: String) : List<String> {
        if(note.isEmpty())
            return getNotesByUser(userName)

        if (notebookDB[userName] == null) {
            notebookDB[userName] = mutableListOf()
        }

        notebookDB[userName]!!.add(note)

        return notebookDB[userName]!!
    }

    fun deleteNote(userName: String, id: Int) : List<String> {
        val list = notebookDB[userName] ?: return EMPTY_LIST
        if (id < list.size) {
            list.removeAt(id)
        }
        return list
    }

    fun editNote(userName: String, id: Int, content: String) : List<String> {
        val list = notebookDB[userName] ?: return EMPTY_LIST
        if (id < list.size) {
            list[id] = content
        }
        return list
    }

    companion object {
        val EMPTY_LIST = listOf("Нет заметок")
    }
}