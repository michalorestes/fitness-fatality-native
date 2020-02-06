package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import com.example.fitnessfatality.data.dao.WorkoutSessionDao
import com.example.fitnessfatality.data.models.workoutSession.WorkoutSession

class WorkoutSessionRepository(private val dao: WorkoutSessionDao) {

    @WorkerThread
    fun insert(session: WorkoutSession): Long {
        return dao.insert(session)
    }
}