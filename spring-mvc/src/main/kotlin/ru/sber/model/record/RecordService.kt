package ru.sber.model.record

interface RecordService {
  fun createRecord(recordDTO: RecordDTO, userId: String)
  fun modifyRecord(recordDTO: RecordDTO, userId: String)
  fun getRecordById(id: Int, userId: String): RecordDTO?
  fun getAllRecord(userId: String): List<RecordDTO>
  fun deleteRecordById(id: Int, userId: String)
  fun search(query: String, userId: String): List<RecordDTO>
}