package com.example.fitnessfatality.ui.myWorkouts.adapters

import android.view.View
import com.example.fitnessfatality.data.models.workout.Workout

interface OnWorkoutListItemClickListener {
    fun onWorkoutSelected(view: View, workout: Workout)
}