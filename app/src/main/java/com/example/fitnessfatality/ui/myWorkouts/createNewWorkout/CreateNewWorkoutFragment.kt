package com.example.fitnessfatality.ui.myWorkouts.createNewWorkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.workout.Workout
import com.example.fitnessfatality.ui.myWorkouts.viewModels.WorkoutViewModel
import kotlinx.android.synthetic.main.fragment_create_new_workout.*

class CreateNewWorkoutFragment : Fragment() {
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        workoutViewModel = ViewModelProviders.of(activity!!).get(WorkoutViewModel::class.java)

        return inflater.inflate(R.layout.fragment_create_new_workout, container, false)
    }

    override fun onStart() {
        super.onStart()
        btn_save_workout.setOnClickListener {
            workoutViewModel.insertWorkout(
                Workout(
                    name = txt_workout_name.text.toString(),
                    workoutIcon = getWorkoutIcon()
                )
            )
        }
    }

    private fun getWorkoutIcon(): Int
    {
        if (radio_group.checkedRadioButtonId == radio_image_a.id) {
            return R.drawable.inverval_workout_icon
        } else if (radio_group.checkedRadioButtonId == radio_image_b.id) {
            return R.drawable.cardio_workout_icon
        }

        return 0
    }
}
