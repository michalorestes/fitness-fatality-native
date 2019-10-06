package com.example.fitnessfatality.ui.screens.workoutDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.ui.screens.workoutDetails.viewModels.WorkoutDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottomsheet_workout_details.view.*
import kotlinx.android.synthetic.main.dialog_bottomsheet_workout_details.view.lbl_primary_muscles

class WorkoutExerciseInfoDialog(
    private val workoutExercisePojo: WorkoutExercisePojo,
    private val workoutDetailsViewModel: WorkoutDetailsViewModel
) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.dialog_bottomsheet_workout_details,
            container,
            false
        )

        //TODO Do not concatenate string in for labels text property
        view.apply {
            lbl_exercise_name.text = workoutExercisePojo.exercise!!.name

            val primaryMuscles =
                "Primary Muscle: " + workoutExercisePojo
                    .exercise!!
                    .primaryMuscleGroup
                    .muscleName
            lbl_primary_muscles.text = primaryMuscles

            val secondaryMuscles =
                "Secondary Muscle: " + workoutExercisePojo
                    .exercise!!
                    .secondaryMuscleGroups
                    .toString()

            lbl_secondary_muscles.text = secondaryMuscles

            txt_reps.setText(workoutExercisePojo.workoutExercise!!.loggingTarget["reps"].toString())
            txt_sets.setText(workoutExercisePojo.workoutExercise!!.loggingTarget["sets"].toString())
            txt_rest.setText(workoutExercisePojo.workoutExercise!!.loggingTarget["rest"].toString())

            btn_dismiss.setOnClickListener {
                dismiss()
            }

            btn_save.setOnClickListener {
                val sets: Int = txt_sets.text.toString().toInt()
                val reps = txt_reps.text.toString().toInt()
                val rest = txt_rest.text.toString().toInt()

                workoutExercisePojo.workoutExercise!!.loggingTarget["reps"] = reps
                workoutExercisePojo.workoutExercise!!.loggingTarget["sets"] = sets
                workoutExercisePojo.workoutExercise!!.loggingTarget["rest"] = rest
                workoutDetailsViewModel.update(workoutExercisePojo.workoutExercise!!)
                dismiss()
            }
        }

        return view
    }
}
