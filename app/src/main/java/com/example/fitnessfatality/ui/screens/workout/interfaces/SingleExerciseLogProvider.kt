package com.example.fitnessfatality.ui.screens.workout.interfaces

import com.example.fitnessfatality.data.models.workoutSession.Log

interface SingleExerciseLogProvider {
    fun getLogValue(): Log
}