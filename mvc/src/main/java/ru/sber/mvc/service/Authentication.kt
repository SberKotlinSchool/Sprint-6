package ru.sber.mvc.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.sber.mvc.entity.Client
import java.util.concurrent.ConcurrentHashMap

@Component
class Authentication {
    private val clients: ConcurrentHashMap<String, String> = ConcurrentHashMap(mapOf("login1" to "password1", "login2" to "password2"))
    private val logger = LoggerFactory.getLogger(javaClass)

    public fun authentication(client: Client): Boolean {
        if (clients[client.getLogin()] == client.getPassword()) {
            logger.info("the client ${client.getLogin()} is authenticated")
            return true
        }
        logger.info("the client ${client.getLogin()} is not authenticated")
        return false
    }
}
