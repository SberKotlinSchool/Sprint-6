package ru.sber.api.dto

import ru.sber.model.record.RecordDTO


data class RecordRequest(
  val name: String,
  val lastName: String,
  val secondName: String,
  val phoneNumber: String,
  val address: RecordDTO.Address
) {
  constructor(recordDTO: RecordDTO) : this(
    name = recordDTO.name,
    lastName = recordDTO.lastName,
    secondName = recordDTO.secondName,
    phoneNumber = recordDTO.phoneNumber,
    address = recordDTO.address,
  )

  fun toRecordDto(): RecordDTO = RecordDTO(
    name = name,
    lastName = lastName,
    secondName = secondName,
    phoneNumber = phoneNumber,
    address = RecordDTO.Address(address.city,
      address.street,
      address.houseNumber,
      address.postcode)
  )

  fun toRecordDto(id: Int): RecordDTO = RecordDTO(id = id,
    name = name,
    lastName = lastName,
    secondName = secondName,
    phoneNumber = phoneNumber,
    address = RecordDTO.Address(address.city,
      address.street,
      address.houseNumber,
      address.postcode)
  )
}
