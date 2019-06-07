package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.dao.ExerciseDao
import com.example.fitnessfatality.data.models.exercise.Exercise

class ExerciseRepository(private val dao: ExerciseDao) {
    private val allExercises = dao.fetchAll()

    @WorkerThread
    suspend fun insert(exercise: Exercise) {
        dao.insert(exercise)
    }

    fun selectAllExercises(): LiveData<List<Exercise>> {
        return allExercises
    }
}