package com.example.fitnessfatality.data.repository

import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.dao.RoutineExerciseDao
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo

class WorkoutRepository(
    private val routineExerciseDao: RoutineExerciseDao
) {
    fun fetchRoutineExecises(routineId: Int): List<RoutineExercisePojo> {
        return routineExerciseDao.findByWorkoutIdBlocking(routineId)
    }
}