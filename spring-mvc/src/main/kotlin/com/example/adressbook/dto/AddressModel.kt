package com.example.adressbook.dto

import com.fasterxml.jackson.annotation.JsonIgnore

data class AddressModel(
//    @set:JsonIgnore
    var id: Long = 0,
    val name: String = "",
    val address: String = ""
)
