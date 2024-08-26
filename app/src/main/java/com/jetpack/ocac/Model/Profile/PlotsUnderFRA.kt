package com.jetpack.ocac.Model.Profile

data class PlotsUnderFRA(
    val accessToIrrigation: AccessToIrrigation,
    val forestproduce: List<Forestproduce>,
    val kharif: List<Kharif>,
    val plotsUnderFRADetails: List<PlotsUnderFRADetail>,
    val rabi: List<Rabi>,
    val summer: List<Summer>
)