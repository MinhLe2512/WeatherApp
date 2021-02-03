package com.example.testweatherapp.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testweatherapp.R
import com.example.testweatherapp.`class`.City
import com.example.testweatherapp.`interface`.AccuWeather
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.recycler_view_search_result.*
import kotlinx.android.synthetic.main.recycler_view_search_result.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseURL = "https://dataservice.accuweather.com/"
const val apiKey = "CzawK6VvXPdqs9ALeioQbWz2guTHF1wz"
const val language = "en-US"

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(search_tool_bar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        val search = menu?.findItem(R.id.nav_search)
        val searchView = search?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                setUpRetroFit(newText!!)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpRetroFit(searchString: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(AccuWeather::class.java)
        val callback = service.getCities(apiKey, searchString, language)
        callback.enqueue(object : Callback<List<City>> {
            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                Toast.makeText(this@SearchActivity, "No result found!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                if (response.code() == 200) {
                    val listCities = response.body()
                    if (listCities.isNullOrEmpty())
                        return
                    rc_view_search_location.adapter = SearchBarAdapter(listCities)
                    rc_view_search_location.layoutManager = LinearLayoutManager(this@SearchActivity)
                }
            }

        })
    }

    inner class SearchBarAdapter(
        private val listOfCities: List<City>
    ) :
        RecyclerView.Adapter<SearchBarAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cityName: TextView = itemView.search_result
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                View.inflate(this@SearchActivity, R.layout.recycler_view_search_result, null)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return listOfCities.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.cityName.text = listOfCities[position].LocalizedName.toString()
            holder.itemView.setOnClickListener {

            }
        }

    }
}