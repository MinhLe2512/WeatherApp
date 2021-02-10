package com.example.testweatherapp.subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testweatherapp.R
import kotlinx.android.synthetic.main.fragments_weather.*

class WeatherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragments_weather, container, false)
    }
    fun setText(str1: String, str2: String) {
        minimum_degree.text = str1
        maximum_degree.text = str2
    }
}