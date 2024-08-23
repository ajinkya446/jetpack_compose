package com.jetpack.ocac.Model

data class ValidateOTP(
    val access_token: String,
    val error: Int,
    val message: String,
    val notification_response: NotificationResponse,
    val success: Int,
    val token_type: String
)

data class NotificationResponse(
    val message: String,
    val response: Response,
    val success: Int
)