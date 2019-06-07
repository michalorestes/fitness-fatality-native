package com.example.fitnessfatality.ui.exerciseDatabase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fitnessfatality.R

class ExerciseDatabaseFragment : Fragment() {

    lateinit var exercisesViewModel: ExercisesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercisesViewModel = ViewModelProviders.of(this).get(ExercisesViewModel::class.java)
        exercisesViewModel.exercises.observe(this, Observer {
            it?.forEach {
                Log.d("Exercise Database-->", it.name)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise_database, container, false)
    }
}
