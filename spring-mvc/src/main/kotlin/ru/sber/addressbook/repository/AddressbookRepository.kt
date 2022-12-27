package ru.sber.addressbook.repository

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import ru.sber.addressbook.models.CLientsInfo

@Repository
class AddressbookRepository {
    private val clients = ConcurrentHashMap<String, CLientsInfo>()

    fun list() = clients.values.toList()

    fun add(client: CLientsInfo) {
        clients[client.name] = client
    }

    fun view(id: String) = clients[id]

    fun edit(id: String, client: CLientsInfo) {
        clients.remove(id)
        clients[client.name] = client
    }
    fun delete(id: String) = clients.remove(id)

}