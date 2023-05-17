package com.example.springmvc.model

import javax.validation.constraints.NotBlank

data class Contact(
    var id: Int = 0,

    @NotBlank(message = "Имя не может быть пустым")
    var name: String = "",

    @NotBlank(message = "Телефон не может быть пустым")
    var phone: String = "",
)
