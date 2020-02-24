package com.example.fitnessfatality.ui.screens.workout.workoutSession

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.models.workoutSession.Workout
import com.example.fitnessfatality.data.repository.WorkoutRepository
import com.example.fitnessfatality.ui.screens.workout.interfaces.UiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class WorkoutManager(
    private val routineId: Long,
    private val timer: Timer,
    val state: State,
    private val uiController: UiController,
    private val workoutRepository: WorkoutRepository
) {
    private val exercises: MutableLiveData<List<RoutineExercisePojo>> = MutableLiveData(listOf())

    init {
        GlobalScope.launch(Dispatchers.Main) {
            val routines = withContext(Dispatchers.Default) {
                workoutRepository.fetchRoutineExcises(routineId)
            }

            exercises.value = routines

            uiController.initialiseViewPager(getExercises().value!!)
        }
    }

    fun next() {
        if (state.isLastSet(exercises = exercises.value!!)) {
            val currentExercise = exercises.value!![state.getExerciseIndex()].routineExercise!!
            currentExercise.exerciseLogs = uiController.getSetRepValue()
            currentExercise.exerciseLogs!!.routineExerciseId = currentExercise.id
        }

        when (true) {
            state.isEndOfSession(exercises.value!!) -> terminateWorkout()
            !state.isLastSet(exercises.value!!) -> state.incrementSetIndex()
            else -> {
                state.moveToNextExercise()
            }
        }
    }

    private fun terminateWorkout() {
        state.resetState()
        //TODO: Code in global scope should be handled somwhere else. It is a mess at the moment
        GlobalScope.launch(Dispatchers.Default) {
            Log.d("+++", "Starting to write to DB")
            val workout =
                Workout(
                    id = 8,
                    routineId = routineId,
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now()
                )
            workout.id = workoutRepository.storeWorkout(workout)
            Log.d("+++", "Saved workout")

            exercises.value!!.forEach { routineExercisePojo ->
                Log.d("+++", "saving logs")

                val log = routineExercisePojo.routineExercise!!.exerciseLogs!!
                log.workoutId = workout.id
                log.id = workoutRepository.storeWorkoutLog(log)
                log.logSets.forEach {
                    it.logId = log.id
                    workoutRepository.storeLogSet(it)
                }
            }

            Log.d("+++", "Completed saving workout to db :)")
        }

        uiController.navigateToEndOfWorkout()
        Log.d("+", "End of workout")
    }

    fun getExercises(): MutableLiveData<List<RoutineExercisePojo>> {
        return exercises
    }
}
