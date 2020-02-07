package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.dao.RoutineExerciseDao
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.models.routine.RoutineExercise

class RoutineExerciseRepository(private val dao: RoutineExerciseDao) {
    val allWorkoutExercises = dao.fetchAll()

    @WorkerThread
    fun insert(routineExercise: RoutineExercise) {
        dao.insert(routineExercise)
    }

    fun findWorkoutExercisesByWorkoutId(workoutId: Int): LiveData<List<RoutineExercisePojo>> {
        return dao.findByRoutineId(workoutId)
    }

    fun findWorkoutExercisesByWorkoutIdBlocking(workoutId: Int): List<RoutineExercisePojo> {
        return dao.findByWorkoutIdBlocking(workoutId)
    }

    fun update(routineExercise: RoutineExercise) {
        dao.update(routineExercise)
    }

    fun delete(routineExercise: RoutineExercise) {
        dao.delete(routineExercise)
    }
}
