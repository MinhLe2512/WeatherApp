package com.example.testweatherapp.subfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testweatherapp.R
import com.example.testweatherapp.`class`.OneDayDailyForecasts
import com.example.testweatherapp.activity.HomeActivity
import kotlinx.android.synthetic.main.layout_air_polluten.*
import kotlinx.android.synthetic.main.recycler_view_air_polluten_row.view.*


class AirAndPollenFragment : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<AirAndPollenRecycler.ViewHolderAirPollutant>? =
        null
    private var listAirPollutant: ArrayList<OneDayDailyForecasts.DailyForecasts.AirAndPollen>? =
        null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_air_polluten, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (listAirPollutant != null)
            outState.putParcelableArrayList("list", listAirPollutant)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        listAirPollutant = savedInstanceState?.getParcelableArrayList("list")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAirPollutant = arguments?.getParcelableArrayList("list")
        rc_view_air_pollutant.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = AirAndPollenRecycler(listAirPollutant!!)
        }
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAirPollutant = arguments?.getParcelableArrayList("list")

    }*/

    fun newInstance(listAirPollutant: ArrayList<OneDayDailyForecasts.DailyForecasts.AirAndPollen>): Fragment {
        val args = Bundle()

        val fragment = AirAndPollenFragment()

        args.putParcelableArrayList("list", listAirPollutant)
        fragment.arguments = args

        return fragment
    }

    inner class AirAndPollenRecycler(private val listAirPollutant: List<OneDayDailyForecasts.DailyForecasts.AirAndPollen>) :
        RecyclerView.Adapter<AirAndPollenRecycler.ViewHolderAirPollutant>() {
        inner class ViewHolderAirPollutant(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtName: TextView = itemView.name
            val txtValue: TextView = itemView.value
            val txtCategory: TextView = itemView.category
            val txtCategoryValue: TextView = itemView.category_value
            val txtType: TextView? = itemView.type
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolderAirPollutant {
            val view =
                View.inflate(parent.context, R.layout.recycler_view_air_polluten_row, null)
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
}