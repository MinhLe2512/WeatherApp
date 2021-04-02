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
        view.btn_switch.setOnClickListener { swap() }
        return view
    }
    fun onUpdate(dayNight: FiveDayForecasts.DailyForecasts) {
        obj = dayNight
        swap()
        btn_switch.visibility = View.VISIBLE
    }

     private fun swap() {
        isDay = if (!isDay) {
            nightUpdate()
            btn_switch.setBackgroundResource(R.drawable.ic_moon)
            true
        } else {
            dayUpdate()
            btn_switch.setBackgroundResource(R.drawable.ic_sun)
            false
        }
    }
    @SuppressLint("SetTextI18n")
    private fun dayUpdate() {
        txtThunderStormValue.text = obj.day.thunderStormProbability.toString() + '%'
        txtRainValue.text = obj.day.rainProbability.toString() + '%'
        txtSnowValue.text = obj.day.snowProbability.toString() + '%'
        txtIceValue.text = obj.day.iceProbability.toString() + '%'
        txtWindSpeedValue.text =
            obj.day.wind.speed.value.toString() + obj.day.wind.speed.unit.toString()
        txtWindGustValue.text =
            obj.day.windGust.speed.value.toString() + obj.day.windGust.speed.unit.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun nightUpdate() {
        txtThunderStormValue.text = obj.night.thunderStormProbability.toString() + '%'
        txtRainValue.text = obj.night.rainProbability.toString() + '%'
        txtSnowValue.text = obj.night.snowProbability.toString() + '%'
        txtIceValue.text = obj.night.iceProbability.toString() + '%'
        txtWindSpeedValue.text =
            obj.night.wind.speed.value.toString() + obj.night.wind.speed.unit.toString()
        txtWindGustValue.text =
            obj.night.windGust.speed.value.toString() + obj.night.wind.speed.unit.toString()
    }
}
