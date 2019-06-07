package com.example.fitnessfatality.ui.exerciseDatabase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.ui.exerciseDatabase.adapters.ExercisesListAdapter
import com.example.fitnessfatality.ui.exerciseDatabase.viewModels.ExercisesViewModel
import kotlinx.android.synthetic.main.fragment_exercise_database.*

class ExerciseDatabaseFragment : Fragment() {

    lateinit var exercisesViewModel: ExercisesViewModel
    lateinit var exercisesListAdapter: ExercisesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercisesViewModel = ViewModelProviders.of(this).get(ExercisesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise_database, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exercisesListAdapter = ExercisesListAdapter()
        val linearLayoutManager = LinearLayoutManager(context)
        (exercises_list as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = exercisesListAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        exercisesViewModel.exercises.observe(this, Observer {
            exercisesListAdapter.updateDataSet(it)
        })
    }
}
