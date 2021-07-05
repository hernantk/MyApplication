package br.hernan.myapplication.domain.dto

import java.time.LocalDate

data class RegisterMemoryDto(
    val date: String,
    val city:String,
    val description:String,
    val latitude:Double,
    val longitude:Double,
    val image: String
)
