package com.example.fitnessfatality.ui.screens.workoutDetails.adapters

import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo

interface OnWorkoutExerciseClickListener {
    fun onWorkoutExerciseInfoClick(workoutExercise: WorkoutExercisePojo)
}