package com.example.ocacapp.Model

data class LanguageModel(
    val `data`: List<LanguageData>,
    val error: Int,
    val lang: String,
    val message: String,
    val success: Int
)

data class LanguageData(
    val name: String,
    val value: String
)