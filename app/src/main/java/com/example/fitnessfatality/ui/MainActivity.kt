package com.example.fitnessfatality.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.fitnessfatality.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val atest: String = "This is something"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        DataBindingUtil
//            .setContentView<OldActivityMainBinding>(this, R.layout.activity_main)
//            .apply {
//                this.lifecycleOwner = this@MainActivity
//                this.test = atest
//        }
        val navController = Navigation.findNavController(this,
            R.id.workout_nav_host_fragment
        )

        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}
