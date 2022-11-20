package ru.sber.kotlinmvc.services

import org.springframework.stereotype.Repository
import ru.sber.kotlinmvc.entities.Client
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

interface ClientRepository {
    fun find(name : String?, address: String?, phone: String?, email: String?) : Collection<Client>

    fun findById(id: Integer) : Optional<Client>

    fun save(client : Client) : Integer

    fun delete(id: Integer)
}



@Repository
class ClientRepositoryImpl: ClientRepository {
    val idGenerator  = AtomicInteger(0)
    val entries = ConcurrentHashMap<Integer, Client>()

    override fun find(name : String?, address: String?, phone: String?, email: String?): Collection<Client> {
        return entries.values.filter {
            it.name == name ?: it.name &&
            it.address == address ?: it.address &&
            it.phone == phone ?: it.phone &&
            it.email == email ?: it.email
        }
    }

    override fun findById(id: Integer): Optional<Client> {
        return Optional.ofNullable(entries.get(id))
    }

    override fun save(client: Client): Integer {
        val id =
        try {
            client.id
        } catch (e: UninitializedPropertyAccessException) {
            client.id = idGenerator.incrementAndGet() as Integer
            client.id
        }

        entries.put(id, client)
        return id
    }

    override fun delete(id: Integer) {
        entries.remove(id)
    }

}