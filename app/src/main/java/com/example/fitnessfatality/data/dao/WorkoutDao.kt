package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessfatality.data.models.workout.Workout

@Dao
interface WorkoutDao {
    @Insert
    fun insert(workout: Workout)

    @Query("SELECT * FROM workout")
    fun selectAll(): LiveData<List<Workout>>

    @Query("DELETE FROM workout")
    fun deleteAll()
}