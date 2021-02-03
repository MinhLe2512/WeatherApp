package com.example.testweatherapp.`interface`

import com.example.testweatherapp.`class`.City
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AccuWeather {
    @GET("locations/v1/cities/autocomplete")
    fun getCities(
        @Query("apikey") key: String,
        @Query("q") cityName: String,
        @Query("language") lan: String
    ): Call<List<City>>
}