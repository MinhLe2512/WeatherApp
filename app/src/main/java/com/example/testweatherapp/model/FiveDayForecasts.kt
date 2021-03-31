package com.example.testweatherapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class FiveDayForecasts(
    @SerializedName("Headline")
    val headLine: Headline,
    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecasts>
) {
    class Headline(
        @SerializedName("EffectiveDate")
        val effectiveDate: String? = null,
        @SerializedName("Text")
        val text: String? = null
    )

    class DailyForecasts(
        @SerializedName("Date")
        val date: String? = null,
        @SerializedName("Temperature")
        val temperature: Temperature,
        @SerializedName("Day")
        val day: Day,
        @SerializedName("Night")
        val night: Night,
        @SerializedName("Sun")
        val sun: Sun,
        @SerializedName("Moon")
        val moon: Moon,
        @SerializedName("RealFeelTemperature")
        val rfTemperature: RealFeelTemperature,
        @SerializedName("AirAndPollen")
        val listOfAirAndPollen: ArrayList<AirAndPollen>
    ) {
        class Sun(
            @SerializedName("Rise")
            val rise: String? = null,
            @SerializedName("Set")
            val set: String? = null
        )

        class Moon(
            @SerializedName("Rise")
            val rise: String? = null,
            @SerializedName("Set")
            val set: String? = null
        )

        class Temperature(
            @SerializedName("Minimum")
            val minimum: Minimum,
            @SerializedName("Maximum")
            val maximum: Maximum
        ) {
            class Minimum(
                @SerializedName("Value")
                val value: Float? = 0.0f,
                @SerializedName("Unit")
                val unit: String? = null
            )

            class Maximum(
                @SerializedName("Value")
                val value: Float? = 0.0f
            )
        }

        class RealFeelTemperature(
            @SerializedName("Minimum")
            val minimum: Minimum,
            @SerializedName("Maximum")
            val maximum: Maximum
        ) {
            class Minimum(
                @SerializedName("Value")
                val value: Float? = 0.0f
            )

            class Maximum(
                @SerializedName("Value")
                val value: Float? = 0.0f
            )
        }


        @Parcelize
        class AirAndPollen(
            @SerializedName("Name")
            val name: String? = null,
            @SerializedName("Value")
            val value: Int? = 0,
            @SerializedName("Category")
            val category: String? = null,
            @SerializedName("CategoryValue")
            val categoryValue: Int? = 0,
            @SerializedName("Type")
            val type: String? = null
        ) : Parcelable

        class Day(
            @SerializedName("ShortPhrase")
            val shortPhrase: String? = null,
            @SerializedName("PrecipitationProbability")
            val precipitationProbability: Float? = 0.0f,
            @SerializedName("ThunderstormProbability")
            val thunderStormProbability: Float? = 0.0f,
            @SerializedName("RainProbability")
            val rainProbability: Float? = 0.0f,
            @SerializedName("SnowProbability")
            val snowProbability: Float? = 0.0f,
            @SerializedName("IceProbability")
            val iceProbability: Float? = 0.0f,
            @SerializedName("Wind")
            val wind: Wind,
            @SerializedName("WindGust")
            val windGust: WindGust
        ) {
            class Wind(
                @SerializedName("Speed")
                val speed: Speed
            ) {
                class Speed(
                    @SerializedName("Value")
                    val value: Float? = 0.0f,
                    @SerializedName("Unit")
                    val unit: String? = null
                )
            }

            class WindGust(
                @SerializedName("Speed")
                val speed: Speed
            ) {
                class Speed(
                    @SerializedName("Value")
                    val value: Float? = 0.0f,
                    @SerializedName("Unit")
                    val unit: String? = null
                )
            }
        }

        class Night(
            @SerializedName("PrecipitationProbability")
            val precipitationProbability: Float? = 0.0f,
            @SerializedName("ThunderstormProbability")
            val thunderStormProbability: Float? = 0.0f,
            @SerializedName("RainProbability")
            val rainProbability: Float? = 0.0f,
            @SerializedName("SnowProbability")
            val snowProbability: Float? = 0.0f,
            @SerializedName("IceProbability")
            val iceProbability: Float? = 0.0f,
            @SerializedName("Wind")
            val wind: Wind,
            @SerializedName("WindGust")
            val windGust: WindGust
        ) {
            class Wind(
                @SerializedName("Speed")
                val speed: Speed
            ) {
                class Speed(
                    @SerializedName("Value")
                    val value: Float? = 0.0f,
                    @SerializedName("Unit")
                    val unit: String? = null
                )

            }

            class WindGust(
                @SerializedName("Speed")
                val speed: Speed
            ) {
                class Speed(
                    @SerializedName("Value")
                    val value: Float? = 0.0f
                )
            }

        }
    }
}