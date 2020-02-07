package com.example.fitnessfatality.ui.screens.workout.workoutSession

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo

class WorkoutManager(
    private val routineId: Int,
    private val timer: Timer,
    val state: State
) {

    private val exercises: MutableLiveData<List<RoutineExercisePojo>> = MutableLiveData(listOf())

    fun next() {
        when(true) {
            isEndOfSession() -> terminateWorkout()
            !isLastSet() -> moveToNextSet()
            else -> moveToNextExercise()
        }
    }

    private fun isEndOfSession(): Boolean {
        val currentExerciseNumberOfSets =
            exercises.value!![state.getExerciseIndex()]
                .routineExercise!!.loggingParameters["sets"]!!.toInt()

        val isLastExercise = (exercises.value!!.size - 1) == state.exerciseIndex.value
        val isLastSetCompleted = currentExerciseNumberOfSets == state.setIndex.value

        return isLastExercise && isLastSetCompleted
    }

    private fun terminateWorkout() {
        state.resetSetIndex()
        state.resetExerciseIndex()
        Log.d("+", "End of workout")
    }

    private fun moveToNextSet() {
        state.incrementSetIndex()
    }

    private fun moveToNextExercise() {
        state.incrementExerciseIndex()
        state.resetSetIndex()
    }

    private fun isLastSet(): Boolean {
        val setsTarget =
            exercises.value!![state.getExerciseIndex()]
                .routineExercise!!
                .loggingParameters["sets"]!!.toInt()

        return state.getSetIndex() >= setsTarget
    }

    fun getExercises():  MutableLiveData<List<RoutineExercisePojo>> {
        return exercises
    }

    fun setExercises(exercises: List<RoutineExercisePojo>) {
        this.exercises.value = exercises
    }
}
