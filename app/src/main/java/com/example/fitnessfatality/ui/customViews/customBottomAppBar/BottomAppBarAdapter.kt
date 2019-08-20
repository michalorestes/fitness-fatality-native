package com.example.fitnessfatality.ui.customViews.customBottomAppBar

import android.view.MenuItem
import android.view.View
import com.example.fitnessfatality.R

open class BottomAppBarAdapter(
    open var isPrimaryBottomAppBar: Boolean = true,
    open var navigationMenu: Int = R.menu.navigation,
    open var fabDrawableResourceId: Int = R.drawable.ic_add_white_24dp,
    open var isGone: Boolean = false,
    open var actionListenerInterface: BottomAppBarActionListenerInterface = object :
        BottomAppBarActionListenerInterface {
        override fun onFloatingActionButtonPress(view: View) {
            return
        }

        override fun onOptionsMenu(menuItem: MenuItem): Boolean {
            return false
        }
    }
)
