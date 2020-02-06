package com.example.fitnessfatality.data.repository

import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.dao.SessionLogDao
import com.example.fitnessfatality.data.models.workoutSession.SessionLog

class SessionLogRepository(private val dao: SessionLogDao) {

    fun selectAll(): LiveData<List<SessionLog>> {
        return dao.selectAll()
    }

    fun insert(log: SessionLog): Long {
        return dao.insert(log)
    }

    fun update(sessionLog: SessionLog): Int {
        return dao.update(sessionLog)
    }
}