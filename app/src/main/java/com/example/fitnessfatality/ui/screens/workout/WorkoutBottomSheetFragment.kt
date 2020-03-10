package com.example.fitnessfatality.ui.screens.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.databinding.FragmentWorkoutBottomSheetBinding
import com.example.fitnessfatality.ui.screens.workout.workoutSession.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_logging_bottomsheet_exercise_entry.view.*
import kotlinx.android.synthetic.main.fragment_workout_bottom_sheet.*

class WorkoutBottomSheetFragment : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var bottomSheetLayoutContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutViewModel = ViewModelProviders.of(parentFragment!!).get(WorkoutViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentWorkoutBottomSheetBinding>(
            inflater,
            R.layout.fragment_workout_bottom_sheet,
            container,
            false
        )

        binding.viewModel = workoutViewModel

        bottomSheetLayoutContainer = binding.exercisesContainer

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        workoutViewModel.getExercises().observe(parentFragment!!, Observer {
            if (it != null) {
                updateBottomSheetUi(it)
            }
        })

        workoutViewModel.getState().exerciseIndex.observe(parentFragment!!, Observer {
            if (it != null) {
                updateExerciseLogs(it)
            }
        })
    }

    private fun updateBottomSheetUi(workoutExercises: List<RoutineExercisePojo>) {
        workoutExercises.forEach {
            val layout = layoutInflater.inflate(
                R.layout.fragment_logging_bottomsheet_exercise_entry,
                bottomSheetLayoutContainer,
                false
            )

            layout.lbl_exercise_name.setText(it.exercise!!.name)
            bottomSheetLayoutContainer.addView(layout)
        }
    }

    private fun updateExerciseLogs(exerciseIndex: Int) {
        val exercisesList = workoutViewModel
            .getExercises()
            .value!!

        if (exercisesList.isNotEmpty()) {
            val previousExerciseIndex = exerciseIndex.minus(1)
            val logs = exercisesList[previousExerciseIndex]
                .routineExercise!!
                .exerciseLogs!!

            val view: LinearLayout =
                exercises_container.getChildAt(previousExerciseIndex) as LinearLayout
            logs.logSets.forEach {
                val logsView = TextView(context!!)
                logsView.text =
                    "Set: " + it.setIndex +
                    " Weight: " + it.weightLifted +
                    " Reps: " + it.numberOfReps

                view.addView(logsView)
            }
        }
    }
}
