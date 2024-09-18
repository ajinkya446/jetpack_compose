package com.jetpack.ocac.Model.AKAI

data class Registration(
    val applicationId: String,
    val `data`: DataX?,
    val id: String,
    val insertInstant: Long,
    val lastLoginInstant: Long,
    val lastUpdateInstant: Long,
    val preferredLanguages: List<String>,
    val roles: List<Any>,
    val tokens: Tokens?,
    val usernameStatus: String,
    val verified: Boolean,
    val verifiedInstant: Long
)