package ru.sbrf.addressbook.core

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class UserRepository : CrudRepository<Long, User> {

    private val users: ConcurrentHashMap<Long, User> = initDatabase()
    private val idGenerator: AtomicLong = AtomicLong(0)

    override fun add(t: User) {
        t.id = idGenerator.incrementAndGet()
        users[t.id] = t
    }

    override fun update(t: User) {
        users[t.id] = t
    }

    override fun get(id: Long): User? {
        return users[id]
    }

    override fun getAll(): List<User> {
        return users.values.toList()
    }

    override fun deleteById(id: Long) {
        users.remove(id)
    }

    private fun initDatabase(): ConcurrentHashMap<Long, User> {

        val adminLogin = "admin"
        val adminPass = "admin"
        val adminId = (adminLogin.hashCode() + adminLogin.hashCode()).toLong()

        val userLogin = "user"
        val userPass = "user"
        val userId = (userLogin.hashCode() + userPass.hashCode()).toLong()

        return ConcurrentHashMap(
            mapOf(
                adminId to User(adminId, adminLogin, adminPass),
                userId to User(userId, userLogin, userPass)
            )
        )
    }

}

@Repository
class EmployeeRepository : CrudRepository<Long, Employee> {

    private val employees = ConcurrentHashMap<Long, Employee>()
    private val idGenerator: AtomicLong = AtomicLong(0)

    override fun add(t: Employee) {
        t.id = idGenerator.incrementAndGet()
        employees[t.id] = t
    }

    override fun update(t: Employee) {
        employees[t.id] = t
    }

    override fun get(id: Long): Employee? {
        return employees[id]
    }

    override fun getAll(): List<Employee> {
        return employees.values.toList()
    }

    override fun deleteById(id: Long) {
        employees.remove(id)
    }

}

interface CrudRepository<ID, T> {

    fun add(t: T)

    fun update(t: T)

    fun deleteById(id: ID)

    fun get(id: ID): T?

    fun getAll(): List<T>

}
