package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import com.example.fitnessfatality.data.dao.RoutineDao
import com.example.fitnessfatality.data.models.routine.Routine

class RoutinesRepository(private val dao: RoutineDao) {
    val allWorkouts = dao.selectAll()

    @WorkerThread
    suspend fun insert(routine: Routine) {
        dao.insert(routine)
    }
}