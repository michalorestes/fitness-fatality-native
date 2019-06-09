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
import com.example.fitnessfatality.R
import com.example.fitnessfatality.ui.OnActivityInteractionInterface

class WorkoutDetailsFragment: Fragment() {

    val args: WorkoutDetailsFragmentArgs by navArgs()
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
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_workout_details, container, false)
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