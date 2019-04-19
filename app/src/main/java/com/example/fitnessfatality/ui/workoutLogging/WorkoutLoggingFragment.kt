package com.example.fitnessfatality.ui.workoutLogging

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fitnessfatality.R
import com.example.fitnessfatality.ui.workoutLogging.viewModels.WorkoutExerciseViewModel

class WorkoutLoggingFragment : Fragment() {
    private lateinit var workoutExerciseViewModel: WorkoutExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutExerciseViewModel = ViewModelProviders.of(activity!!).get(WorkoutExerciseViewModel::class.java)
        workoutExerciseViewModel.allWorkoutExercises.observe(this, Observer { data ->
            data.forEach { element ->
                Log.w("<<-->>", element.toString())
            }

            Log.w("-->>", "---------------------")
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workout_logging, container, false)
    }
}
