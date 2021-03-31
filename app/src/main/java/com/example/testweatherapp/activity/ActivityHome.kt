package com.example.testweatherapp.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.testweatherapp.model.*
import com.example.testweatherapp.subfragment.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.android.synthetic.main.bar_home.*
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.recycler_row_search_result.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ActivityHome : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ItemClickListener {
    private var listSth = listOf<Fragment>()
    private lateinit var view: View
    private lateinit var puWindow: PopupWindow

    private var degreeFragment = FragmentDegree()
    private var airAndPollenFragment = FragmentAirAndPollen()
    private var dayDetailsFragment = FragmentDayDetails()
    private var weatherFragment = FragmentWeather()

    private var isFABOpen = false
    private var isCelsius = false
    private var degreeUnit = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpAddButton()
        listSth = listOf(degreeFragment, airAndPollenFragment, dayDetailsFragment, weatherFragment)

        val adapter = ViewPagerMainAdapter(listSth, supportFragmentManager)

        view_pager_main.adapter = adapter
        view_pager_main.offscreenPageLimit = 4
        setUpActionBar()
    }

    override fun onItemClicked(city: City) {
        Toast.makeText(this@ActivityHome, city.LocalizedName, Toast.LENGTH_SHORT).show()
        setUpOneDayForeCasts(city.Key)
        if(isFABOpen)
            closeFABMenu()
        puWindow.dismiss()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
            val key = data?.getStringExtra("city_key")
            setUpOneDayForeCasts(key)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.fragment_container, FragmentSettings()).commit()
            R.id.widgets -> supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.fragment_container, FragmentWidgets()).commit()
            else -> {
                return false
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun searching(searchView: androidx.appcompat.widget.SearchView) {
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                setUpLocationRequest(newText!!)
                return true
            }
        })
    }

    private fun setUpLocationRequest(searchString: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Search.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(AccuWeather::class.java)
        val callback = service.getCities(Search.apiKey, searchString, Search.language)
        callback.enqueue(object : Callback<List<City>> {
            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                Toast.makeText(this@ActivityHome, "No result found!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                if (response.code() == Search.requestCode) {
                    val listCities = response.body()
                    if (listCities.isNullOrEmpty())
                        return

                    view.recycleView.adapter = SearchBarAdapter(listCities, this@ActivityHome)
                    view.recycleView.layoutManager = LinearLayoutManager(this@ActivityHome)
                }
            }
        })
    }

    private fun setUpOneDayForeCasts(locationKey: String?) {
        val retrofit =
            Retrofit.Builder().baseUrl(Search.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(AccuWeather::class.java)
        service.getForecasts1Day(locationKey, Search.apiKey, Search.language, "true", "true")
            .enqueue(
                object : Callback<FiveDayForecasts> {
                    override fun onFailure(call: Call<FiveDayForecasts>, t: Throwable) {
                        Toast.makeText(this@ActivityHome, "Unable to find location", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(
                        call: Call<FiveDayForecasts>,
                        response: Response<FiveDayForecasts>
                    ) {
                        if (response.code() == Search.requestCode) {
                            val fiveDayForecasts = response.body() ?: return

                            degreeFragment.onUpdate(fiveDayForecasts)
                            airAndPollenFragment.onUpdate(fiveDayForecasts.dailyForecasts.first().listOfAirAndPollen)
                            dayDetailsFragment.onUpdate(fiveDayForecasts.dailyForecasts.first())
                            weatherFragment.onUpdate(fiveDayForecasts.dailyForecasts.first() )
                        }
                    }
                }
            )
    }

    private fun openFABMenu() {
        isFABOpen = true
        btn_add.animate().rotation(360f).duration = 360
        btn_search.animate().translationY(-resources.getDimension(R.dimen.spacing_fab)).duration = 200
        btn_search.animate().alpha(1f).duration = 200

        btn_unit.animate().translationY(-resources.getDimension(R.dimen.spacing_fab) * 2).duration = 220
        btn_unit.animate().alpha(1f).duration = 220

        btn_mode.animate().translationY(-resources.getDimension(R.dimen.spacing_fab) * 3).duration = 240
        btn_mode.animate().alpha(1f).duration = 240

    }

    private fun closeFABMenu() {
        isFABOpen = false
        btn_mode.animate().translationY(0f)
        btn_mode.animate().alpha(0f)
        btn_unit.animate().translationY(0f)
        btn_unit.animate().alpha(0f)
        btn_search.animate().translationY(0f)
        btn_search.animate().alpha(0f)
        btn_add.animate().rotation(0f)
    }


    private fun setUpActionBar() {
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        nav_view.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, tool_bar, R.string.nav_drawer_open, R.string.nav_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpAddButton() {
        btn_add.setOnClickListener {
            if (!isFABOpen) {
                openFABMenu()
                btn_search.setOnClickListener {
                    puWindow = PopupWindow(this@ActivityHome)
                    view =
                        View.inflate(this@ActivityHome, R.layout.activity_search, null)
                    puWindow.isFocusable = true
                    puWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    puWindow.contentView = view
                    puWindow.enterTransition = Slide(Gravity.END)
                    puWindow.showAtLocation(view, Gravity.CENTER, 0, -3000)

                    main_activity.alpha = 0.5f
                    searching(view.searchViewQuery)
                    closeFABMenu()
                    puWindow.setOnDismissListener {
                        main_activity.alpha = 1f
                    }
                }
                btn_unit.setOnClickListener {
                    closeFABMenu()
                    isCelsius = if (!isCelsius) {
                        degreeUnit = false
                        Toast.makeText(this, resources.getString(R.string.degreeFah), Toast.LENGTH_SHORT)
                            .show()
                        true
                    } else {
                        degreeUnit = true
                        Toast.makeText(this, resources.getString(R.string.degreeCel), Toast.LENGTH_SHORT)
                            .show()
                        false
                    }
                }
            } else closeFABMenu()
        }
    }
    inner class ViewPagerMainAdapter(
        private var listlayouts: List<Fragment>,
        fragmentManager: FragmentManager
    ) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return listlayouts[position]
        }

        override fun getCount(): Int = listlayouts.size
    }

    inner class SearchBarAdapter(
        private val listOfCities: List<City>,
        private val itemClickListener: ItemClickListener
    ) :
        RecyclerView.Adapter<SearchBarAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cityName: TextView = itemView.search_result
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                View.inflate(this@ActivityHome, R.layout.recycler_row_search_result, null)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = listOfCities.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.cityName.text = listOfCities[position].LocalizedName.toString()
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClicked(listOfCities[position])
            }
        }
    }
}
