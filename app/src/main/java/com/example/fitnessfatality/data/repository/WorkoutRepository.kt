package com.example.fitnessfatality.data.repository

import androidx.annotation.WorkerThread
import com.example.fitnessfatality.data.dao.LogDao
import com.example.fitnessfatality.data.dao.LogSetDao
import com.example.fitnessfatality.data.dao.RoutineExerciseDao
import com.example.fitnessfatality.data.dao.WorkoutDao
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.models.workoutSession.Log
import com.example.fitnessfatality.data.models.workoutSession.LogSet
import com.example.fitnessfatality.data.models.workoutSession.Workout

class WorkoutRepository(
    private val routineExerciseDao: RoutineExerciseDao,
    private val logDao: LogDao,
    private val logSetDao: LogSetDao,
    private val workoutDao: WorkoutDao
) {
    fun fetchRoutineExcises(routineId: Long): List<RoutineExercisePojo> {
        return routineExerciseDao.findByWorkoutIdBlocking(routineId)
    }

    @WorkerThread
    fun storeWorkoutLog(log: Log): Long {
        return logDao.insert(log)
    }

    @WorkerThread
    fun storeLogSet(logSet: LogSet) {
        logSetDao.insert(logSet)
    }

    fun storeWorkout(workout: Workout): Long {
        return workoutDao.insert(workout)
    }

    @WorkerThread
    fun updateWorkout(workout: Workout): Long {
        return workoutDao.update(workout)
    }
}