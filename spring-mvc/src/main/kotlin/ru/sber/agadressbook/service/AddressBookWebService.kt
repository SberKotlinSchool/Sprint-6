package ru.sber.agadressbook.service

import org.springframework.stereotype.Service
import ru.sber.agadressbook.models.Credentials


@Service
class AddressBookWebService {

    fun checkUser(credentials : Credentials) : Boolean {
        return credentials.password == "123"
    }

}