package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.dao.WorkoutExerciseDao
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.models.workout.WorkoutExercise

class WorkoutExerciseRepository(private val dao: WorkoutExerciseDao) {
    val allWorkoutExercises = dao.fetchAll()

    @WorkerThread
    fun insert(workoutExercise: WorkoutExercise) {
        dao.insert(workoutExercise)
    }

    fun findWorkoutExercisesByWorkoutId(workoutId: Int): LiveData<List<WorkoutExercisePojo>> {
        return dao.findByWorkoutId(workoutId)
    }

    fun findWorkoutExercisesByWorkoutIdBlocking(workoutId: Int): List<WorkoutExercisePojo> {
        return dao.findByWorkoutIdBlocking(workoutId)
    }

    fun update(workoutExercise: WorkoutExercise) {
        dao.update(workoutExercise)
    }

    fun delete(workoutExercise: WorkoutExercise) {
        dao.delete(workoutExercise)
    }
}
