package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import com.example.fitnessfatality.data.dao.SessionLogDao
import com.example.fitnessfatality.data.models.workoutSession.SessionLog

class ExerciseLogRepository(private val dao: SessionLogDao) {
    val allLogs = dao.fetchAll()

    @WorkerThread
    suspend fun insert(sessionLog: SessionLog) {
        dao.insert(sessionLog)
    }
}
