package com.example.fitnessfatality.ui.workoutDetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.ui.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.workoutDetails.adapters.WorkoutExercisesListAdapter
import kotlinx.android.synthetic.main.fragment_workout_details.*

class WorkoutDetailsFragment: Fragment() {

    private val args: WorkoutDetailsFragmentArgs by navArgs()
    private lateinit var onActivityInteractionInterface: OnActivityInteractionInterface
    private lateinit var recyclerViewAdapter: WorkoutExercisesListAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is OnActivityInteractionInterface) {
            onActivityInteractionInterface = context
        } else {
            throw Exception("MyWorkoutsFragments requires OnActivityInteractionInterface")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_workout_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = WorkoutExercisesListAdapter()
        val myLayoutManager = LinearLayoutManager(context)
        (workout_exercises_list as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = myLayoutManager
            adapter = recyclerViewAdapter
        }

    }

    override fun onStart() {
        super.onStart()
        onActivityInteractionInterface.setSecondaryBottomAppBarMenu()
        onActivityInteractionInterface.optionsItemMenuAction = {
            when (it!!.itemId) {
                R.id.nav_exercise_database -> {
                    val action = WorkoutDetailsFragmentDirections.actionToExerciseDatabase(args.workoutId)
                    Navigation
                        .findNavController(activity!!, R.id.workout_nav_host_fragment)
                        .navigate(action)
                }
            }
        }
    }
}