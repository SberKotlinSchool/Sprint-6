package ru.company.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.company.model.Client
import ru.company.repository.AddressBookRepository


interface AddressBookService {
    fun add(client: Client)
    fun getClients(fio: String?, address: String?, phone: String?, email: String?): List<Client>
    fun getClientById(id: Int): Client?
    fun updateClient(client: Client)
    fun deleteClient(id: Int)
}

@Service
class AddressBookServiceImpl @Autowired constructor(val repository: AddressBookRepository) : AddressBookService {
    override fun add(client: Client) {
        repository.add(client)
    }

    override fun getClients(fio: String?, address: String?, phone: String?, email: String?): List<Client> =
        repository.getClients(fio, address, phone, email)

    override fun getClientById(id: Int): Client? = repository.getClientById(id)

    override fun updateClient(client: Client) {
        repository.updateClient(client)
    }

    override fun deleteClient(id: Int) {
        repository.deleteClient(id)
    }


}