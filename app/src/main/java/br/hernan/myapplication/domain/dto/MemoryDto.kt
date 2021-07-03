package br.hernan.myapplication.domain.dto

import java.time.LocalDate

data class MemoryDto(
    val id : String,
    val date:LocalDate,
    val city:String,
    val description:String,
    val image: String
)
