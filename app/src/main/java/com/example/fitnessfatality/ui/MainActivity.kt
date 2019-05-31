package com.example.fitnessfatality.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.fitnessfatality.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnActivityInteractionInterface {
    override var floatingActionButtonAction: () -> Unit = {}

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this,
            R.id.workout_nav_host_fragment
        )

        setSupportActionBar(bottom_app_bar)
        toolbar.setupWithNavController(navController)

        floatingActionButton = fab
        bottomAppBar = bottom_app_bar
    }

    override fun onStart() {
        super.onStart()
        fab.setOnClickListener {
            floatingActionButtonAction()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navigation, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
        }

        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun setFabAction(floatingActionButtonAction: () -> Unit) {
        this.floatingActionButtonAction = floatingActionButtonAction
    }

    override fun setBottomToolBarMenu() {
        bottomAppBar.replaceMenu(R.menu.workout_details_bottom_app_bar_menu)
    }
}
