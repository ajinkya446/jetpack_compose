package com.jetpack.ocac.Model.AKAI

import com.jetpack.ocac.Model.Params

data class AKAIModel(
    val id: String,
    val params: Params,
    val responseCode: String,
    val result: com.jetpack.ocac.Model.AKAI.Result,
    val ts: String
)