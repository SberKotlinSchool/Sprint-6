package ru.sber.springmvc.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.springmvc.dto.Entry
import ru.sber.springmvc.repository.AppRepository

@Service
class AppService @Autowired constructor(private val repository: AppRepository) {

    fun add(entry: Entry) = repository.add(entry)

    fun list(query: String?) = repository.list()
        .apply {
            query?.let { query ->
                filter { it.name.contains(query) }
            }
        }

    fun view(id: String) = repository.view(id)

    fun edit(id: String, entry: Entry) = repository.edit(id, entry)

    fun delete(id: String) = repository.delete(id)
}