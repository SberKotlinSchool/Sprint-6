package ru.sber.model.record

import java.util.concurrent.ConcurrentHashMap

interface RecordDAO {
  fun setUp(records: ConcurrentHashMap<Int, RecordDTO>)
  fun clear()
  fun insert(recordDTO: RecordDTO, userId: String)
  fun update(recordDTO: RecordDTO, userId: String)
  fun get(id: Int, userId: String): RecordDTO?
  fun getAll(userId: String): List<RecordDTO>
  fun search(query: String, userId: String): List<RecordDTO>
  fun delete(id: Int, userId: String)
}