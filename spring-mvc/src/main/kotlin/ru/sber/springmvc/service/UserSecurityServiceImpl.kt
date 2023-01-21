package ru.sber.springmvc.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.GrantedAuthoritySid
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.security.acls.model.MutableAclService
import org.springframework.security.acls.model.ObjectIdentity
import org.springframework.security.acls.model.Sid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import ru.sber.springmvc.dao.AddressBookRepository
import ru.sber.springmvc.domain.Record

@Component
class UserSecurityServiceImpl : UserSecurityService{
    @Autowired
    protected var mutableAclService: MutableAclService? = null

    @Autowired
    lateinit var recordDao: AddressBookRepository

   override fun add(record: Record): Record? {
        val savedRecord: Record? = recordDao.addRecord(record)
        val authentication = SecurityContextHolder.getContext().authentication
        val owner: Sid = PrincipalSid(authentication)
        val oid: ObjectIdentity = ObjectIdentityImpl(record.javaClass, record.id)
        val admin: Sid = GrantedAuthoritySid("ROLE_ADMIN")
        val acl = mutableAclService!!.createAcl(oid)
        acl.owner = owner
        acl.insertAce(acl.entries.size, BasePermission.READ, admin, true)
        mutableAclService!!.updateAcl(acl)
        return savedRecord
    }
}