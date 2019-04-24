package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessfatality.data.models.logging.ExerciseLog

@Dao
interface ExerciseLogDao {
    @Insert
    fun insert(exerciseLog: ExerciseLog)

    @Query("SELECT * FROM exercise_log")
    fun fetchAll(): LiveData<List<ExerciseLog>>

    @Query("DELETE FROM exercise_log")
    fun deleteAll()
}
