package ru.sber.model.record

data class RecordDTO(
  var id: Int = 0,
  var userId: String = "",
  var name: String = "",
  var lastName: String = "",
  var secondName: String = "",
  var phoneNumber: String = "",
  var address: Address = Address()) {

  data class Address(
    var city: String = "",
    var street: String = "",
    var houseNumber: Int = 0,
    var postcode: Int = 0)
}
