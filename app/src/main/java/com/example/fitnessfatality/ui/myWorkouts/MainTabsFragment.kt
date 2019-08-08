package com.example.fitnessfatality.ui.myWorkouts

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.fitnessfatality.R
import com.example.fitnessfatality.ui.mainActivity.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.myWorkouts.adapters.MainTabsAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main_tabs.view.*

class MainTabsFragment: Fragment() {

    lateinit var viewPager: ViewPager
    private lateinit var onActivityInteractionInterface: OnActivityInteractionInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is OnActivityInteractionInterface) {
            onActivityInteractionInterface = context
        } else {
            throw Exception("MyWorkoutsFragments requires OnActivityInteractionInterface")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_tabs, container, false)
        val tabs: TabLayout = view.tabs
        viewPager = view.view_pager
        viewPager.adapter = MainTabsAdapter(childFragmentManager)
        tabs.setupWithViewPager(viewPager)
        Log.d("MainTabs--->", "In main tabs onCreateView")

        return view
    }

    override fun onStart() {
        super.onStart()
        onActivityInteractionInterface.setPrimaryBottomAppBarMenu()
        onActivityInteractionInterface.floatingActionButtonAction = {
            Navigation
                .findNavController(activity as Activity, R.id.workout_nav_host_fragment)
                .navigate(R.id.action_myWorkoutsFragment_to_createNewWorkoutFragment)
        }
        Log.d("MainTabs--->", "In main tabs onStart")
    }
}
