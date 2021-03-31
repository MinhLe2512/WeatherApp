package com.example.testweatherapp.subfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testweatherapp.R
import com.example.testweatherapp.model.FiveDayForecasts
import kotlinx.android.synthetic.main.fragment_today_details.*
import kotlinx.android.synthetic.main.fragment_today_details.view.*
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FragmentDayDetails : Fragment() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    private val formatter2 = SimpleDateFormat("HH:mm a")
    private var isDay = false
    private lateinit var obj: FiveDayForecasts.DailyForecasts

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_today_details, container, false)
        view.img_btn.visibility = View.GONE
        view.img_btn.setOnClickListener{ swap() }
        return view
    }

    private fun swap() {
        isDay = if (!isDay) {
            onUpdateNight()
            img_btn.setBackgroundResource(R.drawable.ic_moon)
            true
        } else {
            onUpdateDay()
            img_btn.setBackgroundResource(R.drawable.ic_sun)
            false
        }
    }
    fun onUpdate(obj: FiveDayForecasts.DailyForecasts) {
        this.obj = obj
        swap()
        img_btn.visibility = View.VISIBLE
    }
    @SuppressLint("SetTextI18n")
    private fun onUpdateDay() {
        val date = formatter.parse(obj.sun.rise)
        sun_rise.text = formatter2.format(date)

        val date2 = formatter.parse(obj.sun.set)
        sun_set.text = formatter2.format(date2)

        precipitationDay.text = obj.day.shortPhrase
        precipitationProbDay.text = obj.day.precipitationProbability.toString() + '%'

    }
    @SuppressLint("SetTextI18n")
    private fun onUpdateNight() {
        val night1 = formatter.parse(obj.moon.rise)
        sun_rise.text = formatter2.format(night1)

        val night2 = formatter.parse(obj.moon.set)
        sun_set.text = formatter2.format(night2)

        precipitationDay.text = obj.day.shortPhrase
        precipitationProbDay.text = obj.night.precipitationProbability.toString() + '%'
    }

}
