package com.example.fitnessfatality.ui.mainActivity.bottomAppBarNavigation

import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.view.*

class BottomAppBarNavigation(
    layout: CoordinatorLayout,
    private val navController: NavController
) : NavController.OnDestinationChangedListener {
    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val bottomAppBar: BottomAppBar = layout.bottom_app_bar
    private val floatingActionButton: FloatingActionButton = layout.fab

    init {
        navController.addOnDestinationChangedListener { navController: NavController, navDestination: NavDestination, bundle: Bundle? ->


        }
    }


}