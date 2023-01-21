package ru.sber.springmvc.service

import org.springframework.stereotype.Service
import ru.sber.springmvc.domain.Record

@Service
interface UserSecurityService {
    fun add(record: Record): Record?
}