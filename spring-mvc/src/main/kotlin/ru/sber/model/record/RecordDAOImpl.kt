package ru.sber.model.record

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class RecordDAOImpl : RecordDAO {

  var records: ConcurrentHashMap<Int, RecordDTO> =
    ConcurrentHashMap(mapOf(
      1 to RecordDTO(1, "1", "name", "lastName", "secondName", "phoneNumber",
        RecordDTO.Address("city", "street", 1, 123456)),
      2 to RecordDTO(2, "1", "name", "lastName", "secondName", "phoneNumber",
        RecordDTO.Address("city", "street", 2, 342345)),
      3 to RecordDTO(3, "1", "name", "lastName", "secondName", "phoneNumber",
        RecordDTO.Address("city", "street", 5, 345125)),
      4 to RecordDTO(4, "2", "name", "lastName", "secondName", "phoneNumber",
        RecordDTO.Address("city", "street", 15, 753564)),
      5 to RecordDTO(5, "3", "name", "lastName", "secondName", "phoneNumber",
        RecordDTO.Address("city", "street", 23, 423467)),
      6 to RecordDTO(6, "3", "name", "lastName", "secondName", "phoneNumber",
        RecordDTO.Address("city", "street", 92, 167424))))

  override fun setUp(records: ConcurrentHashMap<Int, RecordDTO>) {
    this.records.putAll(records)
  }

  override fun clear() {
    records.clear()
  }

  override fun insert(recordDTO: RecordDTO, userId: String) {
    val id = records.filter { it.value.userId == userId }.values.maxOfOrNull { it.id }?.let { it + 1 } ?: 1
    records[records.maxOfOrNull { it.key }?.let { it + 1 } ?: 1] = recordDTO.copy(id = id, userId = userId)
  }

  override fun update(recordDTO: RecordDTO, userId: String) {
    val id = records.filterValues { it.userId == userId && it.id == recordDTO.id }.keys.toList()[0]
    records[id] = recordDTO.copy(userId = userId)
  }

  override fun get(id: Int, userId: String): RecordDTO? =
    records.filter { it.value.userId == userId }.values.find { it.id == id }

  override fun getAll(userId: String): List<RecordDTO> =
    records.filter { it.value.userId == userId }.values.toList()

  override fun search(query: String, userId: String): List<RecordDTO> =
    records.filter { it.value.userId == userId }.filterValues {
      it.name.contains(query) || it.lastName.contains(query)
        || it.secondName.contains(query) || it.phoneNumber.contains(query)
        || it.address.city.contains(query) || it.address.street.contains(query)
        || it.address.houseNumber.toString().contains(query)
        || it.address.postcode.toString().contains(query)
    }.values.toList()

  override fun delete(id: Int, userId: String) {
    records = ConcurrentHashMap(records.filterNot { (_, value) -> value.id == id && value.userId == userId })
  }

}