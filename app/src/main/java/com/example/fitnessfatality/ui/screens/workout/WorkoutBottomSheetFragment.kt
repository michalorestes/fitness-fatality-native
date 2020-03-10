package com.example.fitnessfatality.ui.screens.workout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.databinding.FragmentWorkoutBottomSheetBinding
import com.example.fitnessfatality.ui.screens.workout.workoutSession.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_logging_bottomsheet_exercise_entry.view.*

class WorkoutBottomSheetFragment: Fragment() {

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
        workoutViewModel.getExercises().observe(this, Observer {
            if (it != null) {
                updateBottomSheetUi(it)
            }
        })
    }

    private fun updateBottomSheetUi(workoutExercises: List<RoutineExercisePojo>) {
        Log.d("---->", "Called update bottom sheet UI")
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
}
