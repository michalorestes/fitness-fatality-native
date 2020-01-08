package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface WorkoutSession {
    @Query("SELECT * FROM workout_session")
    fun fetchAll(): LiveData<List<WorkoutSession>>
}