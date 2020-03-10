package com.example.fitnessfatality.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fitnessfatality.data.models.workoutSession.Workout

@Dao
interface WorkoutDao {
    @Insert
    fun insert(workout: Workout): Long

    @Update
    fun update(workout: Workout)

    @Query("DELETE FROM workout")
    fun deleteAll()
}