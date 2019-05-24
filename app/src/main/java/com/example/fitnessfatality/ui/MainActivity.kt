package com.example.fitnessfatality.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fitnessfatality.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnActivityInteractionInterface {
    override var action: () -> Unit = {}

    lateinit var floatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this,
            R.id.workout_nav_host_fragment
        )

        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        floatingActionButton = fab
    }

    override fun onStart() {
        super.onStart()
        fab.setOnClickListener {
            action()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun setFabAction(floatingActionButtonAction: () -> Unit) {
        action = floatingActionButtonAction
    }
}
