package com.example.fitnessfatality.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.fitnessfatality.data.models.workoutSession.LogSet

@Dao
interface LogSetDao {
    @Insert
    fun insert(logSet: LogSet)
}