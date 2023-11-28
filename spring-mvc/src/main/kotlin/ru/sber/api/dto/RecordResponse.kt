package ru.sber.api.dto

import ru.sber.model.record.RecordDTO

data class RecordResponse(
  val id: Int,
  val userId: String,
  val name: String,
  val lastName: String,
  val secondName: String,
  val phoneNumber: String,
  val address: RecordDTO.Address
) {
  companion object {
    fun fromRecordDTO(recordDTO: RecordDTO): RecordResponse =
      RecordResponse(
        recordDTO.id,
        recordDTO.userId,
        recordDTO.name,
        recordDTO.lastName,
        recordDTO.secondName,
        recordDTO.phoneNumber,
        RecordDTO.Address(recordDTO.address.city,
          recordDTO.address.street,
          recordDTO.address.houseNumber,
          recordDTO.address.postcode)
      )
  }
}
