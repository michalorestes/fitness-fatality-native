package com.example.fitnessfatality.ui.workoutDetails.exerciseDatabase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.ui.workoutDetails.exerciseDatabase.adapters.ExercisesListAdapter
import com.example.fitnessfatality.ui.workoutDetails.exerciseDatabase.adapters.OnExerciseListListener
import com.example.fitnessfatality.ui.workoutDetails.exerciseDatabase.viewModels.ExercisesViewModel
import kotlinx.android.synthetic.main.fragment_exercise_database.*

class ExerciseDatabaseFragment : Fragment(), OnExerciseListListener {

    private val args: ExerciseDatabaseFragmentArgs by navArgs()
    private lateinit var exercisesViewModel: ExercisesViewModel
    private lateinit var exercisesListAdapter: ExercisesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercisesViewModel = ViewModelProviders.of(activity!!).get(ExercisesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise_database, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exercisesListAdapter = ExercisesListAdapter(this)
        val linearLayoutManager = LinearLayoutManager(context)
        (exercises_list as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = exercisesListAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ExerciseDatabase-->", args.workoutId.toString())
        exercisesViewModel.exercises.observe(this, Observer {
            exercisesListAdapter.updateDataSet(it)
        })
    }

    override fun onAddExerciseToWorkout(exercise: Exercise) {
        exercisesViewModel.addExerciseToWorkout(exercise, args.workoutId)
    }
}
