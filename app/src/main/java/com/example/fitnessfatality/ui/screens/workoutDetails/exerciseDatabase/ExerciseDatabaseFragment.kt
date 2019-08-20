package com.example.fitnessfatality.ui.screens.workoutDetails.exerciseDatabase

import android.content.Context
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
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarAdapter
import com.example.fitnessfatality.ui.screens.mainActivity.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.screens.workoutDetails.exerciseDatabase.adapters.ExercisesListAdapter
import com.example.fitnessfatality.ui.screens.workoutDetails.exerciseDatabase.adapters.OnExerciseListListener
import com.example.fitnessfatality.ui.screens.workoutDetails.exerciseDatabase.viewModels.ExercisesViewModel
import kotlinx.android.synthetic.main.fragment_exercise_database.*
import java.lang.Exception

class ExerciseDatabaseFragment : Fragment(), OnExerciseListListener {

    private val args: ExerciseDatabaseFragmentArgs by navArgs()
    private lateinit var exercisesViewModel: ExercisesViewModel
    private lateinit var exercisesListAdapter: ExercisesListAdapter

    private lateinit var onActivityInteractionInterface: OnActivityInteractionInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnActivityInteractionInterface) {
            onActivityInteractionInterface = context
        } else {
            throw Exception("Context must be instance of OnActivityInteractionInterface")
        }
    }

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
        onActivityInteractionInterface.setBottomAppBarAdapter(BottomAppBarAdapter(isGone = true))
        exercisesViewModel.exercises.observe(this, Observer {
            exercisesListAdapter.updateDataSet(it)
        })
    }

    override fun onAddExerciseToWorkout(exercise: Exercise) {
        exercisesViewModel.addExerciseToWorkout(exercise, args.workoutId)
    }
}
