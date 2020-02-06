package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fitnessfatality.data.models.workoutSession.SessionLog

@Dao
interface SessionLogDao {
    @Insert
    fun insert(sessionLog: SessionLog): Long

    @Update
    fun update(sessionLog: SessionLog): Int

    @Query("SELECT * FROM exercise_log")
    fun fetchAll(): LiveData<List<SessionLog>>

    @Query("DELETE FROM exercise_log")
    fun deleteAll()

    @Query("SELECT * FROM exercise_log")
    fun selectAll(): LiveData<List<SessionLog>>
}
