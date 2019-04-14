package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessfatality.data.models.Exercise

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    fun fetchAll(): LiveData<List<Exercise>>

    @Insert
    fun insert(exercise: Exercise)

    @Query("DELETE FROM exercise")
    fun deleteAll()
}