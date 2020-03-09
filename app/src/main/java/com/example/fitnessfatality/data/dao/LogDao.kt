package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessfatality.data.models.pojo.LogSetPojo
import com.example.fitnessfatality.data.models.workoutSession.Log

@Dao
interface LogDao {
    @Insert
    fun insert(log: Log): Long

    @Query("DELETE FROM log")
    fun deleteAll()

    @Query(
        "SELECT " +
                "l.*, " +
                "ls.id AS _id, " +
                "ls.logId AS _logId, " +
                "ls.numberOfReps AS _numberOfReps, " +
                "ls.weightLifted AS _weightLifted, " +
                "ls.setIndex AS _setIndex " +
                "FROM log l JOIN log_set ls ON l.id = ls.logId " +
                "WHERE exerciseId = :exerciseId"
    )
    fun fetchLogsByExercise(exerciseId: Long): LiveData<List<LogSetPojo>>
}
