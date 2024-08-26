package com.jetpack.ocac.Model.Profile

data class AccessToIrrigation(
    val accessToIrrigation: String,
    val accessToIrrigationValue: String,
    val typeOfIrrigation: List<TypeOfIrrigation>
)