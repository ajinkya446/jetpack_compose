package com.jetpack.ocacapp.Model

data class ValidateOTP(
    val access_token: String,
    val error: Int,
    val message: String,
    val notification_response: NotificationResponse,
    val success: Int,
    val token_type: String
)