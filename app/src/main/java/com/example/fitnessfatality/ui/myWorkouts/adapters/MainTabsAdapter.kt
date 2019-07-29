package com.example.fitnessfatality.ui.myWorkouts.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.fitnessfatality.ui.dashboard.DashboardFragment
import com.example.fitnessfatality.ui.myWorkouts.MyWorkoutsFragment

class MainTabsAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    private val myWorkoutsFragment = MyWorkoutsFragment()
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
            0 -> return "MY WORKOUTS"
            1 -> return "DASHBOARD"
        }

        return "N/A"
    }
}