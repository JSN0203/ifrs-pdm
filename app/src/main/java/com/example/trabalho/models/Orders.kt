package com.example.trabalho.models

data class Orders (
    val id: Int,
    val orderDate: String,
    val totalPrice: Double,
    val status: String,
    val obs: String
)