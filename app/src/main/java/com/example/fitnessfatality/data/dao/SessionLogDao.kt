package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessfatality.data.models.workoutSession.SessionLog

@Dao
interface SessionLogDao {
    @Insert
    fun insert(sessionLog: SessionLog)

    @Query("SELECT * FROM exercise_log")
    fun fetchAll(): LiveData<List<SessionLog>>

    @Query("DELETE FROM exercise_log")
    fun deleteAll()
}
