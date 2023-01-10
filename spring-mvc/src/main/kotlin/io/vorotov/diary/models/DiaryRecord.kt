package io.vorotov.diary.models

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class DiaryRecord(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val date: LocalDate,
    val message: String
)
