package com.example.fitnessfatality.ui.exerciseDatabase.adapters

import com.example.fitnessfatality.data.models.exercise.Exercise

interface OnExerciseListListener {
    fun onAddExerciseToWorkout(exercise: Exercise)
}