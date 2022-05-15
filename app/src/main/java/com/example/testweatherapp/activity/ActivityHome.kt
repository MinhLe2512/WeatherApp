package com.example.testweatherapp.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.transition.Slide
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testweatherapp.R
import com.example.testweatherapp.`interface`.AccuWeather
import com.example.testweatherapp.`interface`.ItemClickListener
import com.example.testweatherapp.`object`.Search
import com.example.testweatherapp.databinding.ActivityHomeBinding
import com.example.testweatherapp.databinding.ActivityMainBinding
import com.example.testweatherapp.model.*
import com.example.testweatherapp.subfragment.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.android.synthetic.main.bar_home.*
import kotlinx.android.synthetic.main.bar_home.view.*
import kotlinx.android.synthetic.main.fragment_temperature.*
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.recycler_row_search_result.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityHome : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(viewBinding.root)

        SearchForLocation()
    }

    private fun SearchForLocation() {
        viewBinding.toolBar.searchContainer.setOnClickListener {
            Toast.makeText(this, "Containter touched", Toast.LENGTH_SHORT).show()
        }

    }
}
