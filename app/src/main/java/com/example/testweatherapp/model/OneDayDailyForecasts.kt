package com.example.testweatherapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class OneDayDailyForecasts(
    @SerializedName("Headline")
    val headLine: Headline,
    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecasts>
) {
    class Headline(
        @SerializedName("EffectiveDate")
        val effectiveDate: String? = null,
        @SerializedName("EffectiveEpochDate")
        val effectiveEpochDate: Long? = 0,
        @SerializedName("Severity")
        val severity: Long? = 0,
        @SerializedName("Text")
        val text: String? = null,
        @SerializedName("Category")
        val category: String? = null,
        @SerializedName("EndDate")
        val endDate: String? = null,
        @SerializedName("EndEpochDate")
        val endEpochDate: String? = null,
        @SerializedName("MobileLink")
        val mobileLink: String? = null,
        @SerializedName("Link")
        val link: String? = null
    )

    class DailyForecasts(
        @SerializedName("Date")
        val date: String? = null,
        @SerializedName("EpochDate")
        val epochDate: Long? = 0,
        @SerializedName("Sources")
        val sources: List<String>,
        @SerializedName("Temperature")
        val temperature: Temperature,
        @SerializedName("Day")
        val day: Day,
        @SerializedName("Night")
        val night: Night,
        @SerializedName("MobileLink")
        val mobileLink: String? = null,
        @SerializedName("Link")
        val link: String? = null,
        @SerializedName("HoursOfSun")
        val hoursOfSun: Float? = 0.0f,
        @SerializedName("Sun")
        val sun: Sun,
        @SerializedName("Moon")
        val moon: Moon,
        @SerializedName("RealFeelTemperature")
        val rfTemperature: RealFeelTemperature,
        @SerializedName("DegreeDaySummary")
        val ddSummary: DegreeDaySummary,
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
            val set: String? = null,
            @SerializedName("Phase")
            val phase: String? = null,
            @SerializedName("Age")
            val age: Int? = 0
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


        class DegreeDaySummary(
            @SerializedName("Heating")
            val heating: Heating,
            @SerializedName("Cooling")
            val cooling: Cooling
        ) {
            class Heating(
                @SerializedName("Value")
                val value: Int? = 0
            )

            class Cooling(
                @SerializedName("Value")
                val value: Int? = 0
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
            @SerializedName("Icon")
            val icon: Int,
            @SerializedName("IconPhrase")
            val iconPhrase: String,
            @SerializedName("HasPrecipitation")
            val hasPrecipitation: Boolean,
            @SerializedName("ShortPhrase")
            val shortPhrase: String? = null,
            @SerializedName("LongPhrase")
            val longPhrase: String? = null,
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
            val windGust: WindGust,
            @SerializedName("TotalLiquid")
            val totalLiquid: TotalLiquid,
            @SerializedName("Rain")
            val rain: Rain,
            @SerializedName("Snow")
            val snow: Snow,
            @SerializedName("Ice")
            val ice: Ice,
            @SerializedName("CloudCover")
            val cloudCover: Float? = 0.0f
        ) {
            class Wind(
                @SerializedName("Speed")
                val speed: Speed,
                @SerializedName("Direction")
                val direction: Direction
            ) {
                class Speed(
                    @SerializedName("Value")
                    val value: Float? = 0.0f,
                    @SerializedName("Unit")
                    val unit: String? = null,
                    @SerializedName("UnitType")
                    val unitType: Int? = 0
                )

                class Direction(
                    @SerializedName("Degrees")
                    val degrees: Float? = 0.0f,
                    @SerializedName("Localized")
                    val localized: String? = null,
                    @SerializedName("English")
                    val english: String? = null
                )
            }

            class WindGust(
                @SerializedName("Speed")
                val speed: Speed,
                @SerializedName("Direction")
                val direction: Direction
            ) {
                class Speed(
                    @SerializedName("Value")
                    val value: Float? = 0.0f,
                    @SerializedName("Unit")
                    val unit: String? = null,
                    @SerializedName("UnitType")
                    val unitType: Int? = 0
                )

                class Direction(
                    @SerializedName("Degrees")
                    val degrees: Float? = 0.0f,
                    @SerializedName("Localized")
                    val localized: String? = null,
                    @SerializedName("English")
                    val english: String? = null
                )
            }

            class TotalLiquid(
                @SerializedName("Value")
                val value: Float? = 0.0f,
                @SerializedName("Unit")
                val unit: String? = null,
                @SerializedName("UnitType")
                val unitType: Int? = 0
            )

            class Rain(
                @SerializedName("Value")
                val value: Float? = 0.0f,
                @SerializedName("Unit")
                val unit: String? = null,
                @SerializedName("UnitType")
                val unitType: Int? = 0
            )

            class Snow(
                @SerializedName("Value")
                val value: Float? = 0.0f,
                @SerializedName("Unit")
                val unit: String? = null,
                @SerializedName("UnitType")
                val unitType: Int? = 0
            )

            class Ice(
                @SerializedName("Value")
                val value: Float? = 0.0f,
                @SerializedName("Unit")
                val unit: String? = null,
                @SerializedName("UnitType")
                val unitType: Int? = 0
            )
        }

        class Night(
            @SerializedName("Icon")
            val icon: Int,
            @SerializedName("IconPhrase")
            val iconPhrase: String,
            @SerializedName("HasPrecipitation")
            val hasPrecipitation: Boolean,
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
            val windGust: WindGust,
            @SerializedName("TotalLiquid")
            val totalLiquid: TotalLiquid,
            @SerializedName("Rain")
            val rain: Rain,
            @SerializedName("Snow")
            val snow: Snow,
            @SerializedName("Ice")
            val ice: Ice,
            @SerializedName("CloudCover")
            val cloudCover: Float? = 0.0f
        ) {
            class Wind(
                @SerializedName("Speed")
                val speed: Speed,
                @SerializedName("Direction")
                val direction: Direction
            ) {
                class Speed(
                    @SerializedName("Value")
                    val value: Float? = 0.0f,
                    @SerializedName("Unit")
                    val unit: String? = null,
                    @SerializedName("UnitType")
                    val unitType: Int? = 0
                )

                class Direction(
                    @SerializedName("Degrees")
                    val degrees: Float? = 0.0f,
                    @SerializedName("Localized")
                    val localized: String? = null,
                    @SerializedName("English")
                    val english: String? = null
                )
            }

            class WindGust(
                @SerializedName("Speed")
                val speed: Speed,
                @SerializedName("Direction")
                val direction: Direction

            ) {
                class Speed(
                    @SerializedName("Value")
                    val value: Float? = 0.0f,
                    @SerializedName("Unit")
                    val unit: String? = null,
                    @SerializedName("UnitType")
                    val unitType: Int? = 0
                )

                class Direction(
                    @SerializedName("Degrees")
                    val degrees: Float? = 0.0f,
                    @SerializedName("Localized")
                    val localized: String? = null,
                    @SerializedName("English")
                    val english: String? = null
                )
            }

            class TotalLiquid(
                @SerializedName("Value")
                val value: Float? = 0.0f
            )

            class Rain(
                @SerializedName("Value")
                val value: Float? = 0.0f
            )

            class Snow(
                @SerializedName("Value")
                val value: Float? = 0.0f
            )

            class Ice(
                @SerializedName("Value")
                val value: Float? = 0.0f
            )
        }
    }
}