package com.example.fitnessfatality.ui.screens.routineDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.ui.screens.routineDetails.viewModels.RoutineDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottomsheet_workout_details.view.*
import kotlinx.android.synthetic.main.dialog_bottomsheet_workout_details.view.lbl_primary_muscles

class RoutineExerciseInfoDialog(
    private val routineExercisePojo: RoutineExercisePojo,
    private val routineDetailsViewModel: RoutineDetailsViewModel
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
            lbl_exercise_name.text = routineExercisePojo.exercise!!.name

            val primaryMuscles =
                "Primary Muscle: " + routineExercisePojo
                    .exercise!!
                    .primaryMuscleGroup
                    .muscleName
            lbl_primary_muscles.text = primaryMuscles

            val secondaryMuscles =
                "Secondary Muscle: " + routineExercisePojo
                    .exercise!!
                    .secondaryMuscleGroups
                    .toString()

            lbl_secondary_muscles.text = secondaryMuscles

            txt_reps.setText(routineExercisePojo.routineExercise!!.loggingParameters["reps"].toString())
            txt_sets.setText(routineExercisePojo.routineExercise!!.loggingParameters["sets"].toString())
            txt_rest.setText(routineExercisePojo.routineExercise!!.loggingParameters["rest"].toString())

            btn_dismiss.setOnClickListener {
                dismiss()
            }

            btn_save.setOnClickListener {
                val sets: Int = txt_sets.text.toString().toInt()
                val reps = txt_reps.text.toString().toInt()
                val rest = txt_rest.text.toString().toInt()

                routineExercisePojo.routineExercise!!.loggingParameters["reps"] = reps
                routineExercisePojo.routineExercise!!.loggingParameters["sets"] = sets
                routineExercisePojo.routineExercise!!.loggingParameters["rest"] = rest
                routineDetailsViewModel.update(routineExercisePojo.routineExercise!!)
                dismiss()
            }
        }

        return view
    }
}
