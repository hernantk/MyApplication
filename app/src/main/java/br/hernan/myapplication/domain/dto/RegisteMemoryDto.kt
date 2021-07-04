package br.hernan.myapplication.domain.dto

import java.time.LocalDate

data class RegisteMemoryDto(
    val date: String,
    val city:String,
    val description:String,
    val latitude:String,
    val longitude:String,
    val image: String
)
