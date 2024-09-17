package com.example.trabalho.models

data class Orders (
    val id: Int = 0,
    val orderDate: String,
    val totalPrice: Double,
    val status: String,
    val obs: String
)