package com.example.adressbook.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseDTO<out T>(
    val data: T? = null,
    val success: Boolean = true,
    val error: String? = null
)