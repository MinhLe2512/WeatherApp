package com.example.testweatherapp.subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testweatherapp.R
import com.example.testweatherapp.model.OneDayDailyForecasts
import kotlinx.android.synthetic.main.display_temperature.*


class DegreeFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.display_temperature, container, false)
    }

    fun onUpdate(obj: OneDayDailyForecasts.DailyForecasts, severity: String, note: String) {
        minimum_degree.text = obj.temperature.minimum.value.toString()
        maximum_degree.text = obj.temperature.maximum.value.toString()
        unit_type.text = obj.temperature.minimum.unit.toString()
        degree_day.text = obj.rfTemperature.maximum.value.toString()
        degree_night.text = obj.rfTemperature.minimum.value.toString()
        note_content.text = note
    }
}