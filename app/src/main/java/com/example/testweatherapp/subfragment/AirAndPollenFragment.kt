package com.example.testweatherapp.subfragment

import android.content.Context
<<<<<<< HEAD
import android.graphics.Color
import android.graphics.Rect
=======
>>>>>>> 07a3474779f67ec8043b984d97256b795f26fa5c
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
<<<<<<< HEAD
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
=======
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
>>>>>>> 07a3474779f67ec8043b984d97256b795f26fa5c

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_air_polluten, container, false)
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> 07a3474779f67ec8043b984d97256b795f26fa5c
    }

    inner class AirAndPollenRecycler(private val listAirPollutant: List<OneDayDailyForecasts.DailyForecasts.AirAndPollen>) :
        RecyclerView.Adapter<AirAndPollenRecycler.ViewHolderAirPollutant>() {
        inner class ViewHolderAirPollutant(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtName: TextView = itemView.name
            val txtValue: TextView = itemView.value
            val txtCategory: TextView = itemView.category
<<<<<<< HEAD
            val txtType: TextView? = itemView.type
            val cardView: CardView = itemView.color_bar
=======
            val txtCategoryValue: TextView = itemView.category_value
            val txtType: TextView? = itemView.type
>>>>>>> 07a3474779f67ec8043b984d97256b795f26fa5c
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
<<<<<<< HEAD
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
=======
            holder.txtCategoryValue.text = listAirPollutant[position].categoryValue.toString()
            holder.txtType?.text = listAirPollutant[position].type.toString()
        }

>>>>>>> 07a3474779f67ec8043b984d97256b795f26fa5c
    }
}