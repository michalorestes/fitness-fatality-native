package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import com.example.fitnessfatality.data.dao.ExerciseDao
import com.example.fitnessfatality.data.models.exercise.Exercise

class ExerciseRepository(private val dao: ExerciseDao) {
    val allExercises = dao.fetchAll()

    @WorkerThread
    suspend fun insert(exercise: Exercise) {
        dao.insert(exercise)
    }
}