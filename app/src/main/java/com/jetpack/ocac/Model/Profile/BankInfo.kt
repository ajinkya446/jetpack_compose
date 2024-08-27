package com.jetpack.ocac.Model.Profile

data class BankInfo(
    val intBankId: Int,
    val intNatureOfAccount: String,
    val intOwnAccount: String,
    val vchAccountNumber: String,
    val vchAccountholderName: String,
    val vchBankName: String,
    val vchBranchName: String,
    val vchIfsc: String
)