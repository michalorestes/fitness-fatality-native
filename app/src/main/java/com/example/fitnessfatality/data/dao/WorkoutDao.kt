package com.example.fitnessfatality.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.fitnessfatality.data.models.workoutSession.Workout

@Dao
interface WorkoutDao {
    @Insert
    fun insert(workout: Workout): Long
}