package com.example.fitnessfatality.ui.myWorkouts

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.ui.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.myWorkouts.adapters.WorkoutsListAdapter
import com.example.fitnessfatality.ui.myWorkouts.viewModels.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_my_workouts.*

class MyWorkoutsFragment : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var onActivityInterractionInterface: OnActivityInteractionInterface
    private lateinit var recyclerViewAdapter: WorkoutsListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is OnActivityInteractionInterface) {
            onActivityInterractionInterface = context
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
        recyclerViewAdapter = WorkoutsListAdapter()
        val linearLayoutManager = LinearLayoutManager(context)
        (workouts_list as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = recyclerViewAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        onActivityInterractionInterface.setFabAction {
            Navigation
                .findNavController(activity as Activity, R.id.workout_nav_host_fragment)
                .navigate(R.id.action_myWorkoutsFragment_to_createNewWorkoutFragment)
        }

        this.workoutViewModel.allWorkouts.observe(this, Observer {
            if (it != null) {
                recyclerViewAdapter.updateDataSet(it)
            }
        })
    }
}
