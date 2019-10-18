package com.example.fitnessfatality.ui.screens.homeScreen.adapters

import android.view.View
import com.example.fitnessfatality.data.models.workout.Workout

interface OnWorkoutListItemClickListener {
    fun onWorkoutSelected(view: View, workout: Workout)
    fun onWorkoutSessionSelected(view: View, workout: Workout)
}