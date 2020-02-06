package com.example.fitnessfatality.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessfatality.data.models.workoutSession.WorkoutSession

@Dao
interface WorkoutSessionDao {
    @Insert
    fun insert(session: WorkoutSession): Long

    @Query("DELETE FROM workout_session")
    fun deleteAll()
}