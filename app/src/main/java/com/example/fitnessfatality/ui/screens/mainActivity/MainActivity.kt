package com.example.fitnessfatality.ui.screens.mainActivity

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.fitnessfatality.R
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.CustomBottomAppBar
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarAdapter
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnActivityInteractionInterface {

    private lateinit var botttomAppBar: CustomBottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(
            this,
            R.id.workout_nav_host_fragment
        )

        toolbar.setupWithNavController(navController)
        botttomAppBar = custom_bottom_app_bar
    }

    override fun setBottomAppBarAdapter(adapter: BottomAppBarAdapter) {
        botttomAppBar.setAdapter(adapter)
    }

    override fun getActivityApplication(): Application {
        return application
    }
}
