package com.example.fitnessfatality.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessfatality.data.models.workoutSession.Log

@Dao
interface LogDao {
    @Insert
    fun insert(log: Log): Long

    @Query("DELETE FROM log")
    fun deleteAll()
}