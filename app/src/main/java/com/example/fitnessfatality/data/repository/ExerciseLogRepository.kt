package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import com.example.fitnessfatality.data.dao.ExerciseLogDao
import com.example.fitnessfatality.data.models.logging.ExerciseLog

class ExerciseLogRepository(private val dao: ExerciseLogDao) {
    val allLogs = dao.fetchAll()

    @WorkerThread
    suspend fun insert(exerciseLog: ExerciseLog) {
        dao.insert(exerciseLog)
    }
}
