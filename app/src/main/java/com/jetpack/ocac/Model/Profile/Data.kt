package com.jetpack.ocac.Model.Profile

data class Data(
    val animalInfo: AnimalInfo,
    val bankInfo: BankInfo,
    val cropInfo: CropInfo,
    val demoInfo: DemoInfo,
    val fisheryInfo: FisheryInfo,
    val forestryInfo: ForestryInfo,
    val message: String,
    val occupationInfo: OccupationInfo,
    val occupationInfoId: OccupationInfoId,
    val status: String,
    val statusCode: String
)