package com.example.testweatherapp.model

import com.google.gson.annotations.SerializedName

class City(

    @SerializedName("Version")
    val Version: Int = 0,
    @SerializedName("Key")
    val Key: String? = null,
    @SerializedName("Type")
    val Type: String? = null,
    @SerializedName("Rank")
    val Rank: Int = 0,
    @SerializedName("LocalizedName")
    val LocalizedName: String? = "localized_name"
//    @SerializedName("Country")
//    val country: Country,
//    @SerializedName("AdministrativeArea")
//    val area: AdministrativeArea

) {
    class Country(
        @SerializedName("ID")
        val ID: String? = null,
        @SerializedName("LocalizedName")
        val LocalizedName: String
    )

    class AdministrativeArea(
        @SerializedName("ID")
        val ID: String? = null,
        @SerializedName("LocalizedName")
        val LocalizedName: String
    )
}