package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import com.example.fitnessfatality.data.dao.WorkoutDao
import com.example.fitnessfatality.data.models.workout.Workout

class WorkoutRepository(private val dao: WorkoutDao) {
    val allWorkouts = dao.selectAll()

    @WorkerThread
    suspend fun insert(workout: Workout) {
        dao.insert(workout)
    }
}
