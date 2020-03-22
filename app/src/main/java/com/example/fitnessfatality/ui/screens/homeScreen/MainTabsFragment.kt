package com.example.fitnessfatality.ui.screens.homeScreen

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.fitnessfatality.R
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarActionListenerInterface
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarAdapter
import com.example.fitnessfatality.ui.screens.mainActivity.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.screens.homeScreen.adapters.MainTabsAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main_tabs.view.*

class MainTabsFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var onActivityInteractionInterface: OnActivityInteractionInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnActivityInteractionInterface) {
            onActivityInteractionInterface = context
        } else {
            throw Exception("MyWorkoutsFragments requires OnActivityInteractionInterface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_tabs, container, false)
        val tabs: TabLayout = view.tabs
        viewPager = view.view_pager
        viewPager.adapter = MainTabsAdapter(childFragmentManager)
        tabs.setupWithViewPager(viewPager)

        return view
    }
}
