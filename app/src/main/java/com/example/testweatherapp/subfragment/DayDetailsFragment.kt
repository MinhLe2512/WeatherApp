package com.example.testweatherapp.subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testweatherapp.R

class DayDetailsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.display_today_details, container, false)
    }
    fun newInstance(): Fragment{
        val args = Bundle()

        val fragment = DayDetailsFragment()
        //args.putString("")
        fragment.arguments = args
        return fragment
    }
}