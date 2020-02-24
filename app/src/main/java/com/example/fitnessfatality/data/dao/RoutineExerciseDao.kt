package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.models.routine.RoutineExercise

@Dao
interface RoutineExerciseDao {
    @Insert
    fun insert(routineExercise: RoutineExercise)

    @Query("SELECT we.*, " +
            "e.id as _id," +
            "e.name as _name, " +
            "e.exerciseType as _exerciseType ," +
            "e.primaryMuscleGroup as _primaryMuscleGroup, " +
            "e.secondaryMuscleGroups as _secondaryMuscleGroups, " +
            "e.isCustom as _isCustom " +
            "FROM routine_exercise we " +
            "INNER JOIN exercise e ON we.exercise_id = e.id")
    fun fetchAll(): LiveData<List<RoutineExercisePojo>>

    @Query("DELETE FROM routine_exercise")
    fun deleteAll()

    @Query("SELECT we.*, " +
            "e.id as _id," +
            "e.name as _name, " +
            "e.exerciseType as _exerciseType ," +
            "e.primaryMuscleGroup as _primaryMuscleGroup, " +
            "e.secondaryMuscleGroups as _secondaryMuscleGroups, " +
            "e.isCustom as _isCustom " +
            "FROM routine_exercise we " +
            "INNER JOIN exercise e ON we.exercise_id = e.id " +
            "WHERE we.routineId = :routineId")
    fun findByRoutineId(routineId: Long): LiveData<List<RoutineExercisePojo>>

    @Query("SELECT we.*, " +
            "e.id as _id," +
            "e.name as _name, " +
            "e.exerciseType as _exerciseType ," +
            "e.primaryMuscleGroup as _primaryMuscleGroup, " +
            "e.secondaryMuscleGroups as _secondaryMuscleGroups, " +
            "e.isCustom as _isCustom " +
            "FROM routine_exercise we " +
            "INNER JOIN exercise e ON we.exercise_id = e.id " +
            "WHERE we.routineId = :routineId")
    fun findByWorkoutIdBlocking(routineId: Long): List<RoutineExercisePojo>

    @Update
    fun update(routineExercise: RoutineExercise)

    @Delete
    fun delete(routineExercise: RoutineExercise)
}