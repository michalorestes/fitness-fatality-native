package com.example.fitnessfatality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.fitnessfatality.databinding.OldActivityMainBinding
import kotlinx.android.synthetic.main.old_activity_main.*

class MainActivityOld : AppCompatActivity() {

    private val atest: String = "This is something"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.old_activity_main)


//        DataBindingUtil
//            .setContentView<OldActivityMainBinding>(this, R.layout.old_activity_main)
//            .apply {
//                this.lifecycleOwner = this@MainActivityOld
//                this.test = atest
//        }
        val navController = Navigation.findNavController(this, R.id.workout_nav_host_fragment)

        toolbar.setupWithNavController(navController)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }
}
