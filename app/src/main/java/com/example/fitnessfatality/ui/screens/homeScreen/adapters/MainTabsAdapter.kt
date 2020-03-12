package com.example.fitnessfatality.ui.screens.homeScreen.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.fitnessfatality.ui.screens.dashboard.DashboardFragment
import com.example.fitnessfatality.ui.screens.homeScreen.myRoutines.MyRoutinesFragment

class MainTabsAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    private val myWorkoutsFragment = MyRoutinesFragment()
    private val dashboardFragment = DashboardFragment()

    override fun getItem(position: Int): Fragment {
        Log.d("--->", "Tabs adapter getting item")
        when(position) {
            0 -> return myWorkoutsFragment
            1 -> return dashboardFragment
        }

        return myWorkoutsFragment
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> return "MY ROUTINES"
            1 -> return "STATISTICS"
        }

        return "N/A"
    }
}