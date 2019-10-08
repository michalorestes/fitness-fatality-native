package com.example.fitnessfatality.ui.screens.workoutDetails.adapters

import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.models.workout.WorkoutExercise

interface OnWorkoutExerciseClickListener {
    fun onWorkoutExerciseInfoClick(workoutExercise: WorkoutExercisePojo)
    fun onWorkoutExerciseDeleteClick(workoutExercise: WorkoutExercise)
}