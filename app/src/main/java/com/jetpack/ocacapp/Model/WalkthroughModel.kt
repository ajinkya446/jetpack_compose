package com.jetpack.ocac.Model

data class WalkthroughModel(
    val `data`: List<WalkthroughData>,
    val error: Int,
    val message: String,
    val success: Int
)

data class WalkthroughData(
    val created_by_id: Int,
    val created_on: String,
    val deleted_by_id: Any,
    val deleted_on: String,
    val description: String,
    val id: Int,
    val image: String,
    val is_active: Boolean,
    val is_deleted: Boolean,
    val sequence_no: Int,
    val title: String,
    val updated_by_id: Any,
    val updated_on: String
)