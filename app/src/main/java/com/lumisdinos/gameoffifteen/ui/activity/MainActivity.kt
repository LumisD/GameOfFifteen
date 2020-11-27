package com.lumisdinos.gameoffifteen.ui.activity

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import com.lumisdinos.gameoffifteen.R
import com.lumisdinos.gameoffifteen.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.app_bar_main.view.*

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var clockTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val toolbar: Toolbar = viewBinding.barMain.toolbar
        setSupportActionBar(toolbar)

        clockTv = viewBinding.barMain.toolbar.clock

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_list), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}