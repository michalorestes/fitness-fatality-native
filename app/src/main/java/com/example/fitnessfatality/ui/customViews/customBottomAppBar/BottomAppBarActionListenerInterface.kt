package com.example.fitnessfatality.ui.customViews.customBottomAppBar

import android.view.MenuItem
import android.view.View
import android.widget.Toolbar

interface BottomAppBarActionListenerInterface {
    fun onFloatingActionButtonPress(view: View)
    fun onOptionsMenu(menuItem: MenuItem): Boolean
    fun onNavigationClickListener() { return }
}