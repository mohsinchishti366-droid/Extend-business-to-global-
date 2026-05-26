package com.example.model

data class Shipment(
    val id: String,
    val trackingNumber: String,
    val supplierName: String,
    val origin: String,
    val destination: String,
    val status: String,
    val totalCost: Double,
    val currency: String,
    val date: String
)

data class ProductDesign(
    val id: String,
    val name: String,
    val material: String,
    val style: String,
    val description: String,
    val prompt: String
)

data class GeminiMessage(
    val role: String, // "user" or "model"
    val text: String
)
