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
            state.isEndOfSession(exercises.value!!) -> terminateWorkout()
            !state.isLastSet(exercises.value!!) -> state.incrementSetIndex()
            else -> state.moveToNextExercise()
        }
    }

    private fun terminateWorkout() {
        state.resetState()
        Log.d("+", "End of workout")
    }


    fun getExercises():  MutableLiveData<List<RoutineExercisePojo>> {
        return exercises
    }

    fun setExercises(exercises: List<RoutineExercisePojo>) {
        this.exercises.value = exercises
    }
}
