package com.example.testweatherapp.activity

import android.app.Activity
import android.app.Dialog
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
import com.example.testweatherapp.`class`.City
import com.example.testweatherapp.`class`.Degree
import com.example.testweatherapp.`class`.OneDayDailyForecasts
import com.example.testweatherapp.`interface`.AccuWeather
import com.example.testweatherapp.`interface`.ItemClickListener
import com.example.testweatherapp.subfragment.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.recycler_view_search_result.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ItemClickListener {
    //Search API
    private val baseURL = "https://dataservice.accuweather.com/"
    private val apiKey = "CzawK6VvXPdqs9ALeioQbWz2guTHF1wz" //GRgowd5sFATdeTGElatL0HS9cJlLXc1j
    private val language = "en-US"


    private val requestCode: Int = 200
    private var listSth = listOf<Fragment>()
    private lateinit var view: View
    private lateinit var puWindow: PopupWindow

    //DegreeFragment
    private var degree = Degree(
        null, null, null, null, null,
        null, null, null, null, null
    )
    private var degreeFragment = DegreeFragment(degree)
    private lateinit var myDialog: Dialog


    //AỉrAndPollenFragment
    private var listAirAndPollen = listOf<OneDayDailyForecasts.DailyForecasts.AirAndPollen>(
        OneDayDailyForecasts.DailyForecasts.AirAndPollen(null, null, null, null, null)
    )
    private var airAndPollenFragment = AirAndPollenFragment(listAirAndPollen)


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        floating_btn.setOnClickListener {
            puWindow = PopupWindow(this@HomeActivity)
            view =
                View.inflate(this@HomeActivity, R.layout.activity_search, null)
            puWindow.isFocusable = true
            puWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            puWindow.contentView = view
            puWindow.enterTransition = Slide(Gravity.END)
            puWindow.showAtLocation(view, Gravity.CENTER, 0, -3000)

            main_activity.alpha = 0.5f
            searching(view.searchViewQuery)
            puWindow.setOnDismissListener {
                main_activity.alpha = 1f
            }
            //window.showAsDropDown(searchViewQuery)
        }

        setUpActionBar()
    }

    private fun searching(searchView: androidx.appcompat.widget.SearchView) {
        //val searchView = search?.actionView as SearchView
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
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(AccuWeather::class.java)
        val callback = service.getCities(apiKey, searchString, language)
        callback.enqueue(object : Callback<List<City>> {
            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "No result found!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                if (response.code() == 200) {
                    val listCities = response.body()
                    if (listCities.isNullOrEmpty())
                        return
                    view.recycleView.adapter =
                        SearchBarAdapter(listCities, this@HomeActivity)
                    view.recycleView.layoutManager = LinearLayoutManager(this@HomeActivity)
                    view.recycleView.isNestedScrollingEnabled = false
                }
            }
        })
    }

    override fun onItemClicked(city: City) {
        Toast.makeText(this@HomeActivity, city.LocalizedName, Toast.LENGTH_SHORT).show()
        setUpOneDayForeCasts(city.Key)
        puWindow.dismiss()
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
                View.inflate(this@HomeActivity, R.layout.recycler_view_search_result, null)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return listOfCities.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.cityName.text = listOfCities[position].LocalizedName.toString()
            holder.itemView.setOnClickListener {
                itemClickListener.onItemClicked(listOfCities[position])
            }
        }
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
                                degree = Degree(
                                    it.temperature.minimum.value.toString(),
                                    it.temperature.maximum.value.toString(),
                                    it.rfTemperature.minimum.value.toString(),
                                    it.rfTemperature.maximum.value.toString(),
                                    it.rftShade.minimum.value.toString(),
                                    it.rftShade.maximum.value.toString(),
                                    it.temperature.minimum.unit.toString(),
                                    oneDayDailyForecasts.headLine.severity.toString(),
                                    oneDayDailyForecasts.headLine.category.toString(),
                                    oneDayDailyForecasts.headLine.text.toString()
                                )
                                listAirAndPollen = it.listOfAirAndPollen

                                degreeFragment.onUpdate(degree)
                                airAndPollenFragment.onUpdate(listAirAndPollen)

                                listSth = listOf(
                                    degreeFragment,
                                    airAndPollenFragment,
                                    DayDetailsFragment()
                                )
                                val adapter = ViewPagerMainAdapter(
                                    listSth,
                                    supportFragmentManager
                                )
                                view_pager_main.adapter = adapter
                            }
                        }
                    }
                }
            )
    }

    private fun setUpActionBar() {
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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

    private inner class ViewPagerMainAdapter(
        private var listlayouts: List<Fragment>,
        private val fragmentManager: FragmentManager
    ) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return listlayouts[position]
        }

        override fun getCount(): Int {
            return listlayouts.size
        }
    }
}
