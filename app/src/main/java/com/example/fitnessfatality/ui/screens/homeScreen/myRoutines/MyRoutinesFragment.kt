package com.example.fitnessfatality.ui.screens.homeScreen.myRoutines

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.routine.Routine
import com.example.fitnessfatality.ui.screens.mainActivity.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.screens.homeScreen.MainTabsFragmentDirections
import com.example.fitnessfatality.ui.screens.homeScreen.adapters.OnWorkoutListItemClickListener
import com.example.fitnessfatality.ui.screens.homeScreen.myRoutines.adapters.RoutinesListAdapter
import kotlinx.android.synthetic.main.fragment_my_workouts.*

class MyRoutinesFragment : Fragment(),
    OnWorkoutListItemClickListener {
    private lateinit var routinesViewModel: RoutinesViewModel
    private lateinit var recyclerViewAdapter: RoutinesListAdapter
    private lateinit var onActivityInteractionInterface: OnActivityInteractionInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is OnActivityInteractionInterface) {
            onActivityInteractionInterface = context
        } else {
            throw Exception("MyWorkoutsFragments requires OnActivityInteractionInterface")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        routinesViewModel = ViewModelProviders.of(activity!!).get(RoutinesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_workouts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapter =
            RoutinesListAdapter(
                this,
                resources
            )
        val linearLayoutManager = LinearLayoutManager(context)
        (workouts_list as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = recyclerViewAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        this.routinesViewModel.allWorkouts.observe(this, Observer {
            if (it != null) {
                recyclerViewAdapter.updateDataSet(it)
            }
        })
    }

    override fun onWorkoutSelected(view: View, routine: Routine) {
        val action =
            MainTabsFragmentDirections.viewWorkoutDetails(
                routine.id!!,
                routine.name
            )

        view.findNavController().navigate(action)
    }

    override fun onWorkoutSessionSelected(view: View, routine: Routine) {
        val action = MainTabsFragmentDirections.
            actionMainTabsFragmentToWorkoutLoggingFragment(routine.id!!)

        view.findNavController().navigate(action)
    }
}
