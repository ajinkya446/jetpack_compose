package com.jetpack.ocac.Model

data class ValidateAadhaar(
    val error: Int,
    val message: String,
    val success: Int
)

data class Params(
    val aadhar_number: String
)


data class AadhaarValidate(
    val error: Int,
    val message: String,
    val mobile_number: String,
    val success: Int,
    val transaction_id: String
)
