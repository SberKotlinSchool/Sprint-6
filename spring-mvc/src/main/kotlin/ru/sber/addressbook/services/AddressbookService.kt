package ru.sber.addressbook.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.addressbook.models.CLientsInfo
import ru.sber.addressbook.repository.AddressbookRepository

@Service
class AddressbookService @Autowired constructor(private val repository: AddressbookRepository) {

    fun list(query: String?) = repository.list().apply {
                query?.let { query ->
                    filter { it.name.contains(query) }
                }
        }

    fun add(client: CLientsInfo) = repository.add(client)

    fun delete(id: String) = repository.delete(id)

    fun view(id: String) = repository.view(id)

    fun edit(id: String, client: CLientsInfo) = repository.edit(id, client)

}