package com.example.testweatherapp.subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testweatherapp.R
import com.example.testweatherapp.`class`.Degree
import kotlinx.android.synthetic.main.display_temperature.*
import kotlinx.android.synthetic.main.display_temperature.view.*

class DegreeFragment(private var degree: Degree) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.display_temperature, container, false)
        view.minimum_degree.text = degree.minDeg
        view.maximum_degree.text = degree.maxDeg
        view.degree_night.text = degree.trfMin
        view.degree_day.text = degree.trfMax
        view.real_feel_min.text = degree.trfsMin
        view.real_feel_max.text = degree.trfsMax
        view.unit_type.text = degree.unitType
        view.note_content.text = degree.text
        return view
    }

    fun onUpdate(newDegree: Degree) {
        degree.minDeg = newDegree.minDeg
        degree.maxDeg = newDegree.maxDeg
        degree.minDeg = newDegree.trfMin
        degree.trfMax = newDegree.trfMax
        degree.trfsMin = newDegree.trfsMin
        degree.trfsMax = newDegree.trfsMax
        degree.unitType = newDegree.unitType
        degree.text = newDegree.text
        degree.category = newDegree.category
        degree.severity = newDegree.severity
    }
}