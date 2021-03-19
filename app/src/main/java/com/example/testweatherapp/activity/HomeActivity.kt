package com.example.testweatherapp.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.util.DisplayMetrics
import android.util.Size
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
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.recycler_view_search_result.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ItemClickListener {
    private var listSth = listOf<Fragment>()
    private lateinit var view: View
    private lateinit var puWindow: PopupWindow


    private var degreeFragment = DegreeFragment()
    private var airAndPollenFragment = AirAndPollenFragment()
    private var dayDetailsFragment = DayDetailsFragment()

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
            //main_activity.alpha = 0.5f
            searching(view.searchViewQuery)
            puWindow.setOnDismissListener {
                main_activity.alpha = 1f
            }
            //window.showAsDropDown(searchViewQuery)
        }
        listSth = listOf(
            degreeFragment,
            airAndPollenFragment,
            dayDetailsFragment
        )

        val adapter = ViewPagerMainAdapter(
            listSth,
            supportFragmentManager
        )

        view_pager_main.adapter = adapter
        view_pager_main.offscreenPageLimit = 4
        setUpActionBar()


    }

    override fun onItemClicked(city: City) {
        Toast.makeText(this@HomeActivity, city.LocalizedName, Toast.LENGTH_SHORT).show()
        setUpOneDayForeCasts(city.Key)
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
            .baseUrl(Search.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(AccuWeather::class.java)
        val callback = service.getCities(Search.apiKey, searchString, Search.language)
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
//                    view.recycleView.isNestedScrollingEnabled = false
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
        val callback =
            service.getForecasts1Day(locationKey, Search.apiKey, Search.language, "true", "true")
                .enqueue(
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
                            if (response.code() == Search.requestCode) {
                                val oneDayDailyForecasts = response.body()

                                oneDayDailyForecasts!!.dailyForecasts.forEach {
                                    degreeFragment.onUpdate(
                                        it,
                                        oneDayDailyForecasts.headLine.severity.toString(),
                                        oneDayDailyForecasts.headLine.text!!
                                    )
                                    airAndPollenFragment.onUpdate(it.listOfAirAndPollen)
                                    dayDetailsFragment.onUpdate(it)
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

    inner class ViewPagerMainAdapter(
        private var listlayouts: List<Fragment>,
        private val fragmentManager: FragmentManager
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
                View.inflate(this@HomeActivity, R.layout.recycler_view_search_result, null)
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
