package com.jetpack.ocac.Model.Profile

data class Brackish(
    val brackishDetails: List<BrackishDetail>,
    val capture: List<Capture>,
    val culture: List<Culture>
)