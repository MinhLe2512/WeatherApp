package com.example.testweatherapp.subfragment

import android.annotation.SuppressLint
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
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
class FragmentDegree() : Fragment() {
    private val formatterDefault = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    private val formatter = SimpleDateFormat("dd/MM/yyyy")
    private val formatter2 = SimpleDateFormat("dd/MM")

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
        val date = formatterDefault.parse(obj.headLine.effectiveDate!!)
        txt_date.text = formatter.format(date!!)
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
            val txtDate = formatterDefault.parse(listFourDays[position].date!!)
            holder.itemView.txt_date_rc_view.text = formatter2.format(txtDate!!)
        }

        override fun getItemCount(): Int {
            return listFourDays.size
        }

    }
}
