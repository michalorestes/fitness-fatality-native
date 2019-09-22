package com.example.fitnessfatality.ui.screens.homeScreen.myWorkouts

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
import com.example.fitnessfatality.data.models.workout.Workout
import com.example.fitnessfatality.ui.screens.mainActivity.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.screens.homeScreen.MainTabsFragmentDirections
import com.example.fitnessfatality.ui.screens.homeScreen.adapters.OnWorkoutListItemClickListener
import com.example.fitnessfatality.ui.screens.homeScreen.adapters.WorkoutsListAdapter
import com.example.fitnessfatality.ui.screens.homeScreen.viewModels.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_my_workouts.*

class MyWorkoutsFragment : Fragment(),
    OnWorkoutListItemClickListener {
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var recyclerViewAdapter: WorkoutsListAdapter
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
        workoutViewModel = ViewModelProviders.of(activity!!).get(WorkoutViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_workouts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapter = WorkoutsListAdapter(this, resources)
        val linearLayoutManager = LinearLayoutManager(context)
        (workouts_list as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = recyclerViewAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        this.workoutViewModel.allWorkouts.observe(this, Observer {
            if (it != null) {
                recyclerViewAdapter.updateDataSet(it)
            }
        })
    }

    override fun onWorkoutSelected(view: View, workout: Workout) {
        val action =
            MainTabsFragmentDirections.viewWorkoutDetails(
                workout.id!!,
                workout.name
            )
        view.findNavController().navigate(action)
    }
}
