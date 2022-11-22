package ru.sber.ufs.cc.kulinich.springmvc.services

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.ufs.cc.kulinich.springmvc.filters.LoggingFilter
import ru.sber.ufs.cc.kulinich.springmvc.models.AuthModel
import ru.sber.ufs.cc.kulinich.springmvc.repositories.AuthRepository

@Service
class AuthService(@Autowired val authRepo: AuthRepository) {

    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    fun isAuthCorrect(login: String, password : String) : Boolean {
        logger.info("Try to login with $login | $password")
        logger.info(if(authRepo.auth(AuthModel(login, password))) "Success" else "Failed")
        return authRepo.auth(AuthModel(login, password))
    }

}