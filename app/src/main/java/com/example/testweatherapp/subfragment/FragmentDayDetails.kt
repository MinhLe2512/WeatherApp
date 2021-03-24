package com.example.testweatherapp.subfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testweatherapp.R
import com.example.testweatherapp.model.OneDayDailyForecasts
import kotlinx.android.synthetic.main.fragment_today_details.*
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FragmentDayDetails : Fragment() {
    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    @SuppressLint("SimpleDateFormat")
    private val formatter2 = SimpleDateFormat("HH:mm a")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_today_details, container, false)
    }

    fun onUpdate(obj: OneDayDailyForecasts.DailyForecasts) {
        val date = formatter.parse(obj.sun.rise)
        sun_rise.text = formatter2.format(date)

        val date2 = formatter.parse(obj.sun.set)
        sun_set.text = formatter2.format(date2)

        val night1 = formatter.parse(obj.moon.rise)
        moon_rise.text = formatter2.format(night1)

        val night2 = formatter.parse(obj.moon.set)
        moon_set.text = formatter2.format(night2)

        precipitationDay.text = obj.day.shortPhrase
        precipitationProbDay.text = obj.day.precipitationProbability.toString()

        precipitationNight.text = obj.day.shortPhrase
        precipitationProbNight.text = obj.day.precipitationProbability.toString()
    }
}