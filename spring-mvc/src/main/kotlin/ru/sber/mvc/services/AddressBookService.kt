package ru.sber.mvc.services

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.security.acls.jdbc.JdbcMutableAclService
import org.springframework.stereotype.Service
import ru.sber.mvc.data.AddressBookRepository
import ru.sber.mvc.data.Record
import ru.sber.mvc.domain.DomainRecord

@Service
class AddressBookService(
    private val repository: AddressBookRepository,
    private val aclService: JdbcMutableAclService
) {

    fun add(record: DomainRecord): Long =
        record.run {
            val persisted = repository.save(Record(name, phone, address, description, 0))
            createAcl(persisted)
            persisted.id
        }

    private fun createAcl(record: Record) {
        val identity = ObjectIdentityImpl(record)
        val acl = aclService.createAcl(identity)
        acl.insertAce(acl.entries.size, BasePermission.DELETE, PrincipalSid("admin"), true)
        acl.isEntriesInheriting = false

        aclService.updateAcl(acl)
    }

    fun getById(id: Long): DomainRecord =
        repository.findById(id).get().run { DomainRecord(name, phone, address, description, id) }

    fun getAll(): List<DomainRecord> =
        repository.findAll().map { record ->
            record.run { DomainRecord(name, phone, address, description, id) }
        }

    fun editById(id: Long, record: DomainRecord): DomainRecord =
        record.run { repository.save(Record(name, phone, address, description, id)) }
            .run { DomainRecord(name, phone, address, description, id) }

    @PreAuthorize("hasPermission(#id, 'ru.sber.mvc.data.Record','DELETE')")
    fun delete(id: Long) =
        repository.deleteById(id)
}