package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnessfatality.data.models.routine.Routine

@Dao
interface RoutineDao {
    @Insert
    fun insert(routine: Routine)

    @Query("SELECT * FROM routine")
    fun selectAll(): LiveData<List<Routine>>

    @Query("DELETE FROM routine")
    fun deleteAll()
}