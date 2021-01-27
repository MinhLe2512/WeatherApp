package com.example.testweatherapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.PagerAdapter
import com.example.testweatherapp.R
import com.example.testweatherapp.subfragment.SettingsFragment
import com.example.testweatherapp.subfragment.WidgetsFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.view_pager_main_row.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val pagerAdapter = ViewPagerMainAdapter(listSth)
        view_pager_main.adapter = pagerAdapter

        floating_btn.setOnClickListener {
            val intent = Intent(this,  SearchActivity::class.java)
            startActivity(intent)
        }
        setUpActionBar()
    }

    private var listSth =
        listOf(R.drawable.ic_search, R.drawable.ic_settings, R.drawable.ic_widgets)

    private fun setUpViewPagerMain() {
    }

    private inner class ViewPagerMainAdapter(private var list: List<Int>) : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = View.inflate(this@HomeActivity, R.layout.view_pager_main_row, null)
            view.img_test.setImageResource(list[position])
            container.addView(view)
            return view
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return list.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
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
