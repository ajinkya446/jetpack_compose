package com.jetpack.ocacapp.Model

data class ValidateAadhaar(
    val error: Int,
    val message: String,
    val success: Int
)

data class Params(
    val aadhar_number: String
)
