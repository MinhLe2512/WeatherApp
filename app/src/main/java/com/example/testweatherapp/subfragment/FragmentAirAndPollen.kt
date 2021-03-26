package com.example.testweatherapp.subfragment

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testweatherapp.R
import com.example.testweatherapp.model.FiveDayForecasts
import kotlinx.android.synthetic.main.recycler_view_air_polluten.*
import kotlinx.android.synthetic.main.recycler_view_air_polluten_row.view.*


class FragmentAirAndPollen() : Fragment() {
    private var listAirPollutant: List<FiveDayForecasts.DailyForecasts.AirAndPollen>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recycler_view_air_polluten, container, false)
    }


    fun onUpdate(newList: List<FiveDayForecasts.DailyForecasts.AirAndPollen>) {
        listAirPollutant = newList
        rc_view_air_pollutant.adapter?.notifyDataSetChanged()
        rc_view_air_pollutant.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AirAndPollenRecycler(listAirPollutant!!)
        }
        rc_view_air_pollutant.addItemDecoration(MarginItemDecoration(50))
    }

    inner class MarginItemDecoration(private val marginVal: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = marginVal
                }
                bottom = marginVal
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class AirAndPollenRecycler(private val listAirPollutant: List<FiveDayForecasts.DailyForecasts.AirAndPollen>) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_air_polluten_row, parent, false)
            )
        }

        override fun getItemCount(): Int = listAirPollutant.size


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.name.text = listAirPollutant[position].name.toString()
            holder.itemView.value.text = listAirPollutant[position].value.toString()
            holder.itemView.category.text = listAirPollutant[position].category.toString()

            if (listAirPollutant[position].type != null)
                holder.itemView.type.text = listAirPollutant[position].type.toString()
            else
                holder.itemView.type.text = ""

            when (listAirPollutant[position].categoryValue) {
                1, 2 -> holder.itemView.color_bar.setCardBackgroundColor(Color.GREEN)
                3, 4 -> holder.itemView.color_bar.setCardBackgroundColor(Color.YELLOW)
                5, 6 -> holder.itemView.color_bar.setCardBackgroundColor(Color.RED)
                else -> {
                    holder.itemView.color_bar.setCardBackgroundColor(Color.BLACK)
                }
            }
        }
    }
}