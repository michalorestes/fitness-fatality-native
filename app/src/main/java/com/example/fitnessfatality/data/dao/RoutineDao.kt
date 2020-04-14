package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Update
import com.example.fitnessfatality.data.models.routine.Routine

@Dao
interface RoutineDao {
    @Insert
    fun insert(routine: Routine)

    @Query("SELECT * FROM routine ORDER BY sequenceId ASC")
    fun selectAll(): LiveData<List<Routine>>

    @Query("DELETE FROM routine")
    fun deleteAll()

    @Delete
    fun delete(routine: Routine)

    @Query("SELECT COUNT(*) FROM routine")
    fun getNumberOfRoutines(): Int

    @Update
    fun updateAll(vararg routine: Routine)
}