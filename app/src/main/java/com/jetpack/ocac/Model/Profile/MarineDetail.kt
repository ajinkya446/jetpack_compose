package com.jetpack.ocac.Model.Profile

data class MarineDetail(
    val fisheryId: Int,
    val fishingWithoutBoat: Int,
    val fishingWithoutBoatValue: String,
    val marineId: Int,
    val nonMotorizedNoOfBoat: String,
    val traditionalBoat: Int,
    val traditionalBoatValue: String,
    val traditionalNoOfBoat: String,
    val traditionalNonMotorizedBoat: Int,
    val traditionalNonMotorizedBoatValue: String,
    val trawler: Int,
    val trawlerNoOfBoat: String,
    val trawlerValue: String
)