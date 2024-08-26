package com.jetpack.ocac.Model.Profile

data class CaptureX(
    val fishingWithoutBoat: Int,
    val fishingWithoutBoatValue: String,
    val traditionalMotorizedBoat: Int,
    val traditionalMotorizedBoatValue: String,
    val traditionalMotorizedNoOfBoat: String,
    val traditionalNonMotorizedBoat: Int,
    val traditionalNonMotorizedBoatValue: String,
    val traditionalNonMotorizedNoOfBoat: String,
    val typeOfWaterbody: List<TypeOfWaterbody>
)