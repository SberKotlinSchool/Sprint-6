package io.vorotov.diary.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import io.vorotov.diary.models.DiaryRecord
import io.vorotov.diary.repository.DiaryRepository
import java.time.LocalDate

@Service
class DiaryService @Autowired constructor(private val repository: DiaryRepository) {

    fun list(date: LocalDate?, message: String?) = repository.list { dr ->
        (dr.date == (date ?: dr.date)) ||
                dr.message == (message ?: dr.message)
    }

    fun add(client: DiaryRecord) = repository.add(client)

    fun delete(date: LocalDate) = repository.delete(date)

    fun view(date: LocalDate) = repository.view(date)

    fun edit(date: LocalDate, client: DiaryRecord) = repository.edit(date, client)

}