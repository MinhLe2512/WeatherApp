package com.example.testweatherapp.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.testweatherapp.R
import com.example.testweatherapp.`class`.OneDayDailyForecasts
import com.example.testweatherapp.`interface`.AccuWeather
import com.example.testweatherapp.subfragment.SettingsFragment
import com.example.testweatherapp.subfragment.WidgetsFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.display_temperature.*
import kotlinx.android.synthetic.main.layout_air_polluten.*
import kotlinx.android.synthetic.main.recycler_view_air_polluten_row.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val requestCode: Int = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val adapter = ViewPagerMainAdapter(listSth)
        view_pager_main.adapter = adapter

        floating_btn.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivityForResult(intent, requestCode)
        }
        setUpActionBar()
        rc_view_air_pollutant.adapter = AirPollutantRecycler(listTest)
        rc_view_air_pollutant.layoutManager = LinearLayoutManager(this@HomeActivity)
    }

    private var listSth = listOf(
        R.layout.layout_air_polluten,
        R.layout.display_today_details,
        R.layout.display_temperature
    )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
            val key = data?.getStringExtra("city_key")
            setUpOneDayForeCasts(key)
        }
    }
    private val obj1 = OneDayDailyForecasts.DailyForecasts.AirAndPollen(
        "AirQuality",
        67,
        "Moderate",
        2,
        "Particle Pollution"
    )
    private val obj2 = OneDayDailyForecasts.DailyForecasts.AirAndPollen(
        "Grass",
        0,
        "Low",
        1,
        "Test"
    )
    private val listTest: List<OneDayDailyForecasts.DailyForecasts.AirAndPollen> =
        listOf(
            obj1, obj2
        )

    private fun setUpOneDayForeCasts(locationKey: String?) {
        val retrofit =
            Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(AccuWeather::class.java)
        val callback =
            service.getForecasts1Day(locationKey, apiKey, language, "true", "true").enqueue(
                object : Callback<OneDayDailyForecasts> {
                    override fun onFailure(call: Call<OneDayDailyForecasts>, t: Throwable) {
                        Toast.makeText(
                            this@HomeActivity,
                            "Unable to find location",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onResponse(
                        call: Call<OneDayDailyForecasts>,
                        response: Response<OneDayDailyForecasts>
                    ) {
                        if (response.code() == 200) {
                            val oneDayDailyForecasts = response.body()
                            oneDayDailyForecasts!!.dailyForecasts.forEach {
                                setUpDailyForeCasts(
                                    it.temperature.minimum.value.toString(),
                                    it.temperature.maximum.value.toString(),
                                    it.rfTemperature.minimum.value.toString(),
                                    it.rfTemperature.maximum.value.toString(),
                                    it.rftShade.minimum.value.toString(),
                                    it.rftShade.maximum.value.toString(),
                                    it.listOfAirAndPollen
                                )
                            }
                            setUpHeadlines(
                                oneDayDailyForecasts.headLine.severity.toString(),
                                oneDayDailyForecasts.headLine.category.toString(),
                                oneDayDailyForecasts.headLine.text.toString()
                            )
                        }
                    }
                }
            )
    }

    private fun setUpHeadlines(severity: String, category: String, text: String) {
        note.text = text
    }

    private fun setUpDailyForeCasts(
        temp_min: String,
        temp_max: String,
        real_feel_min: String,
        real_feel_max: String,
        real_feel_shade_min: String,
        real_feel_shade_max: String,
        listOfAirPollutant: List<OneDayDailyForecasts.DailyForecasts.AirAndPollen>
    ) {
        minimum_degree.text = temp_min + "°"
        maximum_degree.text = temp_max + "°"
        temp_real_feel_minimum.text = real_feel_min + "°"
        temp_real_feel_maximum.text = real_feel_max + "°"
        temp_real_feel_shade_minimum.text = real_feel_shade_min + "°"
        temp_real_feel_shade_maximum.text = real_feel_shade_max + "°"
        rc_view_air_pollutant.adapter = AirPollutantRecycler(listOfAirPollutant)
        rc_view_air_pollutant.layoutManager = LinearLayoutManager(this@HomeActivity)
    }

    inner class AirPollutantRecycler(private val listAirPollutant: List<OneDayDailyForecasts.DailyForecasts.AirAndPollen>) :
        RecyclerView.Adapter<AirPollutantRecycler.ViewHolderAirPollutant>() {
        inner class ViewHolderAirPollutant(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtName: TextView = itemView.name
            val txtValue: TextView = itemView.value
            val txtCategory: TextView = itemView.category
            val txtCategoryValue: TextView = itemView.category_value
            val txtType: TextView? = itemView.type
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAirPollutant {
            val view =
                View.inflate(this@HomeActivity, R.layout.recycler_view_air_polluten_row, null)
            return ViewHolderAirPollutant(view)
        }

        override fun getItemCount(): Int {
            return listAirPollutant.size
        }

        override fun onBindViewHolder(holder: ViewHolderAirPollutant, position: Int) {
            holder.txtName.text = listAirPollutant[position].name.toString()
            holder.txtValue.text = listAirPollutant[position].value.toString()
            holder.txtCategory.text = listAirPollutant[position].category.toString()
            holder.txtCategoryValue.text = listAirPollutant[position].categoryValue.toString()
            holder.txtType?.text = listAirPollutant[position].type.toString()
        }
    }


    private inner class ViewPagerMainAdapter(
        private var listLayouts: List<Int>
    ) : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view1 = layoutInflater.inflate(R.layout.display_temperature, container, false)
            val view2 = layoutInflater.inflate(R.layout.display_today_details, container, false)
            val view3 =
                layoutInflater.inflate(R.layout.layout_air_polluten, container, false)
            val viewArr = listOf<View>(view1, view2, view3)
            container.addView(viewArr[position])
            return viewArr[position]
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return listLayouts.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val obj = `object` as View
            container.removeView(obj)
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(tool_bar)
        //supportActionBar?.setDisplayShowTitleEnabled(false)
        //supportActionBar?.setTitle(R.string.str_help)

        nav_view.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            tool_bar,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
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
                .replace(R.id.fragment_container, SettingsFragment()).commit()
            R.id.widgets -> supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.fragment_container, WidgetsFragment()).commit()
            else -> {
                return false
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
