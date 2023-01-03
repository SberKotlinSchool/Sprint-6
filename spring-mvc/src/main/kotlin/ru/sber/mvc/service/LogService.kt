package ru.sber.mvc.service

import org.springframework.stereotype.Service
import ru.sber.mvc.data.Contact
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class LogService {
    private val logs = ConcurrentHashMap<LocalDateTime, String>()

    fun add(request: String){
        logs[LocalDateTime.now()] = request
    }

    fun getAll(): Map<LocalDateTime, String> = logs
}