package com.example.dev_myscheduler

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dev_myscheduler.databinding.ActivityMainBinding
import com.example.dev_myscheduler.ui.gallery.ScheduleViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_privacy_policy, R.id.nav_credentials, R.id.nav_rooms),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.appBarMain.fab.setOnClickListener { view ->
            showGroupSelectionMenu(view)
        }
    }

    private fun showGroupSelectionMenu(view: View) {
        val scheduleViewModel = ViewModelProvider(this)[ScheduleViewModel::class.java]
        val groups = scheduleViewModel.getAvailableGroups()

        if (groups.isEmpty()) {
            Toast.makeText(this, "No groups available yet", Toast.LENGTH_SHORT).show()
            return
        }

        val popup = PopupMenu(this, view)
        groups.forEachIndexed { index, group ->
            popup.menu.add(0, index, index, group)
        }

        popup.setOnMenuItemClickListener { item ->
            val selectedGroup = item.title.toString()
            scheduleViewModel.setPreferredGroup(selectedGroup) // Set the selected group and fetch new schedule
            Toast.makeText(this, "Group selected: $selectedGroup", Toast.LENGTH_SHORT).show()
            true
        }

        popup.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
