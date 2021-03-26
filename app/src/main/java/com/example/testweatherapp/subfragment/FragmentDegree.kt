package com.example.testweatherapp.subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testweatherapp.R
import com.example.testweatherapp.model.FiveDayForecasts
import kotlinx.android.synthetic.main.fragment_temperature.*
import kotlinx.android.synthetic.main.recycler_row_four_days.view.*


class FragmentDegree() : Fragment() {
    private lateinit var listFourDays: List<FiveDayForecasts.DailyForecasts>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_temperature, container, false)
    }

    fun onUpdate(obj: FiveDayForecasts) {
        minimum_degree.text = obj.dailyForecasts.first().temperature.minimum.value.toString()
        maximum_degree.text = obj.dailyForecasts.first().temperature.maximum.value.toString()
        unit_type.text = obj.dailyForecasts.first().temperature.minimum.unit.toString()
        degree_day.text = obj.dailyForecasts.first().rfTemperature.maximum.value.toString()
        degree_night.text = obj.dailyForecasts.first().rfTemperature.minimum.value.toString()
        txt_date.text = obj.headLine.effectiveDate
        note_content.text = obj.headLine.text

        listFourDays = obj.dailyForecasts
        rc_view_4_days.adapter = FourDaysRecyclerAdapter(listFourDays)
        rc_view_4_days.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class FourDaysRecyclerAdapter(private var listFourDays: List<FiveDayForecasts.DailyForecasts>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_row_four_days, parent, false)
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.txt_min_degree_rc_view.text = listFourDays[position].temperature.minimum.value.toString()
            holder.itemView.txt_max_degree_rc_view.text = listFourDays[position].temperature.maximum.value.toString()
            holder.itemView.txt_date_rc_view.text = listFourDays[position].date.toString()
        }

        override fun getItemCount(): Int {
            return listFourDays.size
        }

    }
}