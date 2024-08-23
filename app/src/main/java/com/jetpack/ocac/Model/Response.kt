package com.jetpack.ocac.Model

data class Response(
    val canonical_ids: Int,
    val failure: Int,
    val multicast_id: Long,
    val results: List<Result>,
    val success: Int
)