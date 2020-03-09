package com.example.fitnessfatality.ui.screens.workout.interfaces

import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.models.workoutSession.Log

interface UiController {
    fun initialiseViewPager(data: List<RoutineExercisePojo>)
    fun getExerciseLog(): Log
    fun navigateToEndOfWorkout()
}