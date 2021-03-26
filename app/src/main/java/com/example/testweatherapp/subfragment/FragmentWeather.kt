package com.example.testweatherapp.subfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testweatherapp.R
import com.example.testweatherapp.model.FiveDayForecasts
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.fragment_weather.view.*

class FragmentWeather : Fragment() {
    private lateinit var obj: FiveDayForecasts.DailyForecasts
    private var isDay: Boolean = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = View.inflate(context, R.layout.fragment_weather, null)
        view.btn_switch.visibility = View.GONE
        view.btn_switch.setOnClickListener {
            Swap()
        }
        return view
    }
    fun onUpdate(dayNight: FiveDayForecasts.DailyForecasts) {
        obj = dayNight
        Swap()
        btn_switch.visibility = View.VISIBLE
    }

    fun Swap() {
        isDay = if (!isDay) {
            nightUpdate()
            true
        } else {
            dayUpdate()
            false
        }
    }
    @SuppressLint("SetTextI18n")
    fun dayUpdate(
    ) {
        thunder_storm_prob_value.text = obj.day.thunderStormProbability.toString()
        rain_prob_value.text = obj.day.rainProbability.toString()
        snow_prob_value.text = obj.day.snowProbability.toString()
        ice_prob_value.text = obj.day.iceProbability.toString()
        wind_speed_value.text =
            obj.day.wind.speed.value.toString() + obj.day.wind.speed.unit.toString()
        wind_gust_speed_value.text =
            obj.day.windGust.speed.value.toString() + obj.day.windGust.speed.unit.toString()
    }

    @SuppressLint("SetTextI18n")
    fun nightUpdate(
    ) {
        thunder_storm_prob_value.text = obj.night.thunderStormProbability.toString()
        rain_prob_value.text = obj.night.rainProbability.toString()
        snow_prob_value.text = obj.night.snowProbability.toString()
        ice_prob_value.text = obj.night.iceProbability.toString()
        wind_speed_value.text =
            obj.night.wind.speed.value.toString() + obj.night.wind.speed.unit.toString()
        wind_gust_speed_value.text =
            obj.night.windGust.speed.value.toString() + obj.night.windGust.speed.unit.toString()
    }
}