package com.fedorskvortsov.companies.domain.entity

fun Company.toSimpleCompany() = SimpleCompany(
    id = id,
    name = name,
    img = img
)

data class Company(
    val id: Int,
    val name: String,
    val img: String,
    val description: String,
    val lat: Double,
    val lon: Double,
    val www: String,
    val phone: String,
)
