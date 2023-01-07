package io.vorotov.diary.repository

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import io.vorotov.diary.models.DiaryRecord
import java.time.LocalDate
import java.util.function.Predicate

@Repository
class DiaryRepository {
    private val diaryRecords = ConcurrentHashMap<LocalDate, DiaryRecord>()

    fun list(predicate: (DiaryRecord) -> Boolean) = diaryRecords.values.toList().filter(predicate)

    fun add(diaryRecord: DiaryRecord) {
        diaryRecords[diaryRecord.date] = diaryRecord
    }

    fun view(date: LocalDate) = diaryRecords[date]

    fun edit(date: LocalDate, diaryRecord: DiaryRecord) {
        diaryRecords.remove(date)
        diaryRecords[diaryRecord.date] = diaryRecord
    }

    fun delete(date: LocalDate) = diaryRecords.remove(date)

}