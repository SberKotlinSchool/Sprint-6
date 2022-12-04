package ru.sbrf.addressbook.core

import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface AddressBookService {

    fun addEmployee(employee: Employee)

    fun getEmployees(
        firstName: String?,
        lastName: String?,
        phone: String?,
        email: String?
    ): List<Employee>

    fun getEmployeeById(id: Long): Employee?

    fun updateEmployee(employee: Employee)

    fun deleteEmployeeById(id: Long)

}

interface UserValidationService {
    fun checkUserAccess(user: User): Boolean
}

@Service
class AddressBookServiceImpl @Autowired constructor(val repository: EmployeeRepository) :
    AddressBookService {

    override fun addEmployee(employee: Employee) = repository.add(employee)

    override fun getEmployees(
        firstName: String?,
        lastName: String?,
        phone: String?,
        email: String?
    ): List<Employee> {
        return repository.getAll()
            .filter { StringUtils.isBlank(firstName) || it.firstName == firstName }
            .filter { StringUtils.isBlank(lastName) || it.lastName == lastName }
            .filter { StringUtils.isBlank(phone) || it.phone == phone }
            .filter { StringUtils.isBlank(email) || it.email == email }
    }

    override fun getEmployeeById(id: Long): Employee? = repository.get(id)

    override fun updateEmployee(employee: Employee) = repository.update(employee)

    override fun deleteEmployeeById(id: Long) = repository.deleteById(id)

}

@Service
class UserValidationServiceImpl @Autowired constructor(val repository: UserRepository) :
    UserValidationService {

    override fun checkUserAccess(user: User): Boolean {
        return repository.get(user.id)?.password == user.password
    }

}

