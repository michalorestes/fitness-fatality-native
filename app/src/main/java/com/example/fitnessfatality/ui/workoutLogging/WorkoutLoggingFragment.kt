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
import com.example.fitnessfatality.ui.workoutLogging.viewModels.ExerciseViewModel

class WorkoutLoggingFragment : Fragment() {
    private lateinit var viewModel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(ExerciseViewModel::class.java)
        viewModel.allExercises.observe(this, Observer {data ->
            data.forEach { exercise ->
                Log.w("-->>", exercise.id.toString())
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workout_logging, container, false)
    }
}