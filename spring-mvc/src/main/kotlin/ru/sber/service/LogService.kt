package ru.sber.service

import org.springframework.stereotype.Service
import ru.sber.data.Contact
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