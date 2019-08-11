package com.example.fitnessfatality.ui.screens.myWorkouts.adapters

import android.view.View
import com.example.fitnessfatality.data.models.workout.Workout

interface OnWorkoutListItemClickListener {
    fun onWorkoutSelected(view: View, workout: Workout)
}