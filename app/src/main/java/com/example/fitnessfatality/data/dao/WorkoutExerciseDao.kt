package com.example.fitnessfatality.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.models.workout.WorkoutExercise

@Dao
interface WorkoutExerciseDao {
    @Insert
    fun insert(workoutExercise: WorkoutExercise)

    @Query("SELECT we.*, " +
            "e.id as _id," +
            "e.name as _name, " +
            "e.exerciseType as _exerciseType ," +
            "e.primaryMuscleGroup as _primaryMuscleGroup, " +
            "e.secondaryMuscleGroups as _secondaryMuscleGroups, " +
            "e.isCustom as _isCustom " +
            "FROM workout_exercise we " +
            "INNER JOIN exercise e ON we.exercise_id = e.id")
    fun fetchAll(): LiveData<List<WorkoutExercisePojo>>

    @Query("DELETE FROM workout_exercise")
    fun deleteAll()

    @Query("SELECT we.*, " +
            "e.id as _id," +
            "e.name as _name, " +
            "e.exerciseType as _exerciseType ," +
            "e.primaryMuscleGroup as _primaryMuscleGroup, " +
            "e.secondaryMuscleGroups as _secondaryMuscleGroups, " +
            "e.isCustom as _isCustom " +
            "FROM workout_exercise we " +
            "INNER JOIN exercise e ON we.exercise_id = e.id " +
            "WHERE we.workoutId = :workoutId")
    fun findByWorkoutId(workoutId: Int): LiveData<List<WorkoutExercisePojo>>

    @Query("SELECT we.*, " +
            "e.id as _id," +
            "e.name as _name, " +
            "e.exerciseType as _exerciseType ," +
            "e.primaryMuscleGroup as _primaryMuscleGroup, " +
            "e.secondaryMuscleGroups as _secondaryMuscleGroups, " +
            "e.isCustom as _isCustom " +
            "FROM workout_exercise we " +
            "INNER JOIN exercise e ON we.exercise_id = e.id " +
            "WHERE we.workoutId = :workoutId")
    fun findByWorkoutIdBlocking(workoutId: Int): List<WorkoutExercisePojo>

    @Update
    fun update(workoutExercise: WorkoutExercise)

    @Delete
    fun delete(workoutExercise: WorkoutExercise)
}