package com.jetpack.ocac.Model.AKAI

data class User(
    val token: String,
    val tokenExpirationInstant: Long,
    val user: UserX
)