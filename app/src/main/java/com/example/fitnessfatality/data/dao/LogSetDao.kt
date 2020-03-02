package com.example.fitnessfatality.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessfatality.data.models.workoutSession.LogSet

@Dao
interface LogSetDao {
    @Insert
    fun insert(logSet: LogSet)

    @Query("DELETE FROM log_set")
    fun deleteAll()
}