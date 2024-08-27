package com.jetpack.ocac.Model.Profile

data class OccupationInfoId(
    val activity: List<Int>,
    val cropCultivator: List<String>,
    val labourType: Int,
    val noOfRec: Int,
    val occupation: List<Int>,
    val profession: List<String>
)