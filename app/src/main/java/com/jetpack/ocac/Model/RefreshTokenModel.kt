package com.jetpack.ocac.Model

data class RefreshTokenModel(
    val access_token: String,
    val error: Int,
    val exp: String,
    val message: String,
    val success: Int
)