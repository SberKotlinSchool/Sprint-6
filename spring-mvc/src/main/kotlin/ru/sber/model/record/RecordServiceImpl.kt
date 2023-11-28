package ru.sber.model.record

import org.springframework.stereotype.Service

@Service
class RecordServiceImpl(private val repository: RecordDAO) : RecordService {
  override fun createRecord(recordDTO: RecordDTO, userId: String) {
    repository.insert(recordDTO, userId)
  }

  override fun modifyRecord(recordDTO: RecordDTO, userId: String) {
    repository.update(recordDTO, userId)
  }

  override fun getRecordById(id: Int, userId: String): RecordDTO? =
    repository.get(id, userId)

  override fun getAllRecord(userId: String): List<RecordDTO> = repository.getAll(userId)

  override fun deleteRecordById(id: Int, userId: String) {
    repository.delete(id, userId)
  }

  override fun search(query: String, userId: String): List<RecordDTO> =
    repository.search(query, userId)
}