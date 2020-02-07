package com.example.fitnessfatality.ui.screens.routineDetails.adapters

import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.models.routine.RoutineExercise

interface OnRoutineExerciseClickListener {
    fun onWorkoutExerciseInfoClick(routineExercise: RoutineExercisePojo)
    fun onWorkoutExerciseDeleteClick(routineExercise: RoutineExercise)
}