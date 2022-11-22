package ru.sber.ufs.cc.kulinich.springmvc.repositories

import org.springframework.stereotype.Repository
import ru.sber.ufs.cc.kulinich.springmvc.models.AuthModel
import java.util.concurrent.ConcurrentHashMap

@Repository
class AuthRepository {
    private val db = ConcurrentHashMap<String, String>()

    init {
        db["admin"] = "password"
    }

    fun auth(model : AuthModel) : Boolean {
        return db[model.login] == model.password
    }

}