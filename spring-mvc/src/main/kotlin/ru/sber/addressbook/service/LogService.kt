package ru.sber.addressbook.service

import org.springframework.stereotype.Service
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