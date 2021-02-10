package com.example.testweatherapp.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.*
import com.example.testweatherapp.R
import com.example.testweatherapp.`class`.OneDayDailyForecasts
import com.example.testweatherapp.`interface`.AccuWeather
import com.example.testweatherapp.subfragment.SettingsFragment
import com.example.testweatherapp.subfragment.WeatherFragment
import com.example.testweatherapp.subfragment.WidgetsFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragments_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.math.max


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val requestCode: Int = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val pagerAdapter = ViewPagerMainAdapter(supportFragmentManager, listSth)
        view_pager_main.adapter = pagerAdapter

        floating_btn.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivityForResult(intent, requestCode)
        }
        setUpActionBar()
    }

    private var listSth = listOf(SettingsFragment(), WidgetsFragment(), WeatherFragment())
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
            val key = data?.getStringExtra("city_key")
            setUpOneDayForeCasts(key)
        }
    }

    private fun setUpOneDayForeCasts(locationKey: String?) {
        val retrofit =
            Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(AccuWeather::class.java)
        val callback =
            service.getForecasts1Day(locationKey, apiKey, language, "true", "false").enqueue(
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
                            minimum_degree.text = oneDayDailyForecasts!!.headLine.text.toString()
                        }
                    }
                }
            )
    }

    private inner class ViewPagerMainAdapter(
        private var fragmentManager: FragmentManager,
        private var list: List<Fragment>
    ) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): Fragment {
            return list[position]
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
