package com.jetpack.ocac.Model.AKAI

data class UserX(
    val active: Boolean,
    val connectorId: String,
    val `data`: DataX,
    val id: String,
    val insertInstant: Long,
    val lastLoginInstant: Long,
    val lastUpdateInstant: Long,
    val memberships: List<Any>,
    val passwordChangeRequired: Boolean,
    val passwordLastUpdateInstant: Long,
    val preferredLanguages: List<Any>,
    val registrations: List<Registration>,
    val tenantId: String,
    val timezone: String,
    val twoFactor: TwoFactor,
    val uniqueUsername: String,
    val username: String,
    val usernameStatus: String,
    val verified: Boolean,
    val verifiedInstant: Long
)