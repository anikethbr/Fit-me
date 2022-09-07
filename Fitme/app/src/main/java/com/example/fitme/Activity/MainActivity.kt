package com.example.fitme.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.fitme.R
import com.example.fitme.fragment.BmiFragment
import com.example.fitme.fragment.DashboardFragment
import com.example.fitme.fragment.HistoryFragment
import com.example.fitme.fragment.ProfileFragment
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

//    variables for the layout elements
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var frameLayout: FrameLayout
    private lateinit var tvWelcome: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        initializing the variables for the layout elements
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        navigationView = findViewById(R.id.navigationView)
        frameLayout = findViewById(R.id.frameLayout)
//        getting the shared preferences for the app
        sharedPreferences = getSharedPreferences("fitme_preferences", MODE_PRIVATE)

//        to display the toolbar
        setUpToolbar()

//        for the hamburger icon
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

//        registering the icon as the listener
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

//        to set the default checked item in the navigtion menu
        navigationView.setCheckedItem(R.id.dashboard)

//        setting the welcome text in the drawer
        val header = navigationView.getHeaderView(0)
        tvWelcome = header.findViewById(R.id.tvWelcome)
        tvWelcome.text = "Welcome ${
            sharedPreferences.getString("name", null).toString()
                .uppercase(Locale.getDefault())
        }"

//        opens dashboard fragment as the app opens
        openDashboard()

//        to control the highlighted item
        var previousItem: MenuItem? = null

//        selection listener for the navigation menu items
        navigationView.setNavigationItemSelectedListener {
            if (previousItem != null)
                previousItem = it
            it.isCheckable = true
            it.isChecked = true

            when (it.itemId) {
                R.id.dashboard -> {
                    openDashboard()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfileFragment())
                        .commit()
                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.history -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HistoryFragment())
                        .commit()
                    supportActionBar?.title = "History"
                    drawerLayout.closeDrawers()
                }
                R.id.bmi -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, BmiFragment())
                        .commit()
                    supportActionBar?.title = "BMI"
                    drawerLayout.closeDrawers()
                }
                R.id.logout -> {
//                    display an alert message to confirm logout
                    val dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("Logout")
                    dialog.setMessage("Are you sure to logout?")
                    dialog.setPositiveButton("Logout") { text, listener ->
                        val sharedPreferences = getSharedPreferences(
                            "fitme_preferences",
                            MODE_PRIVATE
                        )

//                        setting the value of isLogin as false
                        sharedPreferences.edit().putBoolean("isLogin", false).apply()
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    dialog.setNegativeButton("Cancel") { text, listener ->

                    }
                    dialog.setOnDismissListener {

                    }
                    dialog.create()
                    dialog.show()
                }
            }
            return@setNavigationItemSelectedListener true
        }

    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
//        for the home button on the toolbar
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        adding functionality for the hamburger icon
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDashboard() {
//        replacing the fragment in the frame layout and closing the drawer
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, DashboardFragment())
            .commit()
        supportActionBar?.title = "Dashboard"
//        setting the checked item in the navigation menu
        navigationView.setCheckedItem(R.id.dashboard)
        drawerLayout.closeDrawers()
    }

    override fun onBackPressed() {
//        to navigate to dashboard fragment from any other fragment
//        else close the app
        when (supportFragmentManager.findFragmentById(R.id.frameLayout)) {
            !is DashboardFragment -> openDashboard()
            else -> super.onBackPressed()
        }
    }
}