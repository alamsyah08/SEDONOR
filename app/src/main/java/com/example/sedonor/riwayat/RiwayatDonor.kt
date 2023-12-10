package com.example.sedonor.riwayat

data class RiwayatDonor(
    val beratBadan: String = "",
    val denyutNadi: String = "",
    val kadarHemoglobin: String = "",
    val status: Boolean? = false,
    val suhuTubuh: String = "",
    val tanggalDonor: String = "",
    val tekananDarahDiastolik: String = "",
    val tekananDarahSistolik: String = "",
    val tempatDonor: String = ""
)
