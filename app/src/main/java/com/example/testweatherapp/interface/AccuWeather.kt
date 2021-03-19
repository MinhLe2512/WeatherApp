package com.example.testweatherapp.`interface`

import com.example.testweatherapp.model.City
import com.example.testweatherapp.model.OneDayDailyForecasts
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccuWeather {
    @GET("locations/v1/cities/autocomplete")
    fun getCities(
        @Query("apikey") key: String,
        @Query("q") cityName: String,
        @Query("language") lan: String
    ): Call<List<City>>

    @GET("forecasts/v1/daily/1day/{locationKey}")
    fun getForecasts1Day(
        @Path("locationKey") locationKey: String?,
        @Query("apikey") apikey: String,
        @Query("language") lan: String,
        @Query("details") det: String,
        @Query("metric") met: String
    ): Call<OneDayDailyForecasts>
}