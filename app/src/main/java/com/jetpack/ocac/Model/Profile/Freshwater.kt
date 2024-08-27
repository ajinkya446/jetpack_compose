package com.jetpack.ocac.Model.Profile

data class Freshwater(
    val capture: List<CaptureX>,
    val culture: List<Culture>,
    val freshwaterDetails: List<FreshwaterDetail>
)