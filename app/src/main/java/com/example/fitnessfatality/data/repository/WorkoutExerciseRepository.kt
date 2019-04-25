package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import com.example.fitnessfatality.data.dao.WorkoutExerciseDao
import com.example.fitnessfatality.data.models.workout.WorkoutExercise

class WorkoutExerciseRepository(private val dao: WorkoutExerciseDao) {
    val allWorkoutExercises = dao.fetchAll()

    @WorkerThread
    fun insert(workoutExercise: WorkoutExercise) {
        dao.insert(workoutExercise)
    }
}