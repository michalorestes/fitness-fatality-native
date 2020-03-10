package com.example.fitnessfatality.ui.screens.workout.workoutSession

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
    private val workout: Workout = Workout(routineId = routineId, startTime = LocalDateTime.now())

    init {
        GlobalScope.launch(Dispatchers.Main) {
            val workoutId = withContext(Dispatchers.Default) {
                workoutRepository.storeWorkout(workout)
            }

            val routines = withContext(Dispatchers.Default) {
                workoutRepository.fetchRoutineExcises(routineId)
            }

            exercises.value = routines
            workout.id = workoutId
            uiController.initialiseViewPager(getExercises().value!!)
        }
    }

    fun next() {
        if (state.isLastSet(exercises.value!!)) {
            val currentExercise = exercises.value!![state.getExerciseIndex()].routineExercise!!
            currentExercise.exerciseLogs = uiController.getExerciseLog()
            state.moveToNextExercise()
        } else {
            state.incrementSetIndex()
        }

        if (state.isEndOfSession(exercises.value!!)) {
            terminateWorkout()
        }
    }

    private fun terminateWorkout() {
        state.resetState()
        //TODO: Code in global scope should be handled somewhere else. It is a mess at the moment
        GlobalScope.launch(Dispatchers.Default) {
            workout.endTime = LocalDateTime.now()
            workoutRepository.updateWorkout(workout)

            exercises.value!!.forEach { routineExercisePojo ->
                val log = routineExercisePojo.routineExercise!!.exerciseLogs!!
                log.workoutId = workout.id
                log.id = workoutRepository.storeWorkoutLog(log)
                log.logSets.forEach {
                    it.logId = log.id
                    workoutRepository.storeLogSet(it)
                }
            }
        }
        uiController.navigateToEndOfWorkout()
    }

    fun getExercises(): MutableLiveData<List<RoutineExercisePojo>> {
        return exercises
    }
}
