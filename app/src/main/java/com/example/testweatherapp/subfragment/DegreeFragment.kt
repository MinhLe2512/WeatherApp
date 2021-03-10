package com.example.testweatherapp.subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testweatherapp.R
<<<<<<< HEAD
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
=======
import kotlinx.android.synthetic.main.display_temperature.*
import kotlinx.android.synthetic.main.display_temperature.view.*

class DegreeFragment : Fragment() {
    private var minDeg: String? = ""
    private var maxDeg: String? = ""
    private var trfMin: String? = ""
    private var trfMax: String? = ""
    private var trfsMin: String? = ""
    private var trfsMax: String? = ""
    private var category: String? = ""
    private var severity: String? = ""
    private var text: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.display_temperature, container, false)
        view.minimum_degree.text = minDeg
        view.maximum_degree.text = maxDeg
        view.temp_real_feel_minimum.text = trfMin
        view.temp_real_feel_maximum.text = trfMax
        view.temp_real_feel_shade_minimum.text = trfsMin
        view.temp_real_feel_shade_maximum.text = trfsMax
        view.note.text = text
        return view
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (minDeg != null) {
            outState.putString("temp_min", minDeg)
            outState.putString("temp_max", maxDeg)
            outState.putString("real_feel_min", trfMin)
            outState.putString("real_feel_max", trfMax)
            outState.putString("real_feel_shade_min", trfsMin)
            outState.putString("real_feel_shade_max", trfsMax)
            outState.putString("severity", severity)
            outState.putString("category", category)
            outState.putString("text", text)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        minDeg = savedInstanceState?.getString("temp_min")
        maxDeg = savedInstanceState?.getString("temp_max")
        trfMin = savedInstanceState?.getString("real_feel_min")
        trfMax = savedInstanceState?.getString("real_feel_max")
        trfsMin = savedInstanceState?.getString("real_feel_shade_min")
        trfsMax = savedInstanceState?.getString("real_feel_shade_max")
        severity = savedInstanceState?.getString("severity")
        category = savedInstanceState?.getString("category")
        text = savedInstanceState?.getString("text")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        minDeg = arguments?.getString("temp_min")
        maxDeg = arguments?.getString("temp_max")
        trfMin = arguments?.getString("real_feel_min")
        trfMax = arguments?.getString("real_feel_max")
        trfsMin = arguments?.getString("real_feel_shade_min")
        trfsMax = arguments?.getString("real_feel_shade_max")
        severity = arguments?.getString("severity")
        category = arguments?.getString("category")
        text = arguments?.getString("text")
    }

    fun newInstance(
        temp_min: String,
        temp_max: String,
        real_feel_min: String,
        real_feel_max: String,
        real_feel_shade_min: String,
        real_feel_shade_max: String,
        severity: String,
        category: String,
        text: String
    ): Fragment {
        val args = Bundle()

        val fragment = DegreeFragment()
        args.putString("temp_min", temp_min)
        args.putString("temp_max", temp_max)
        args.putString("real_feel_min", real_feel_min)
        args.putString("real_feel_max", real_feel_max)
        args.putString("real_feel_shade_min", real_feel_shade_min)
        args.putString("real_feel_shade_max", real_feel_shade_max)
        args.putString("severity", severity)
        args.putString("category", category)
        args.putString("text", text)

        fragment.arguments = args
        return fragment
>>>>>>> 07a3474779f67ec8043b984d97256b795f26fa5c
    }
}