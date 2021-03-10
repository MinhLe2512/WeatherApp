package com.example.testweatherapp.subfragment

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testweatherapp.R
import com.example.testweatherapp.`class`.Degree
import com.example.testweatherapp.`class`.OneDayDailyForecasts
import com.example.testweatherapp.activity.HomeActivity
import kotlinx.android.synthetic.main.layout_air_polluten.*
import kotlinx.android.synthetic.main.recycler_view_air_polluten_row.*
import kotlinx.android.synthetic.main.recycler_view_air_polluten_row.view.*


class AirAndPollenFragment(
    private var listAirPollutant: List<OneDayDailyForecasts.DailyForecasts.AirAndPollen>
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_air_polluten, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc_view_air_pollutant.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = AirAndPollenRecycler(listAirPollutant)
        }
        rc_view_air_pollutant.addItemDecoration(MarginItemDecoration(50))
    }

    fun onUpdate(newList: List<OneDayDailyForecasts.DailyForecasts.AirAndPollen>) {
        listAirPollutant = newList
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
                left = marginVal
                right = marginVal
                bottom = marginVal
            }
        }
    }

    inner class AirAndPollenRecycler(private val listAirPollutant: List<OneDayDailyForecasts.DailyForecasts.AirAndPollen>) :
        RecyclerView.Adapter<AirAndPollenRecycler.ViewHolderAirPollutant>() {
        inner class ViewHolderAirPollutant(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtName: TextView = itemView.name
            val txtValue: TextView = itemView.value
            val txtCategory: TextView = itemView.category
            val txtType: TextView? = itemView.type
            val cardView: CardView = itemView.color_bar
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
            if (listAirPollutant[position].type.toString() == "null")
                holder.txtType?.text = ""
            else
                holder.txtType?.text = listAirPollutant[position].type.toString()
            when (listAirPollutant[position].categoryValue) {
                1, 2 -> holder.cardView.setCardBackgroundColor(Color.GREEN)
                3, 4 -> holder.cardView.setCardBackgroundColor(Color.YELLOW)
                5, 6 -> holder.cardView.setCardBackgroundColor(Color.RED)
                else -> {
                    holder.cardView.setCardBackgroundColor(Color.BLACK)
                }
            }
        }
    }
}