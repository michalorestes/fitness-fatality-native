package com.example.fitnessfatality.ui.screens.mainActivity

import android.app.Application
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarAdapter

interface OnActivityInteractionInterface {
    fun setBottomAppBarAdapter(adapter: BottomAppBarAdapter)
    fun getActivityApplication(): Application
}