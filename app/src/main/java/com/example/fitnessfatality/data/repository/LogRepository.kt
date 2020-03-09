package com.example.fitnessfatality.data.repository

import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.dao.LogDao
import com.example.fitnessfatality.data.models.pojo.LogSetPojo

class LogRepository(private val logDao: LogDao) {
    fun fetchLogsByExercise(exerciseId: Long): LiveData<List<LogSetPojo>> {
        return logDao.fetchLogsByExercise(exerciseId)
    }
}