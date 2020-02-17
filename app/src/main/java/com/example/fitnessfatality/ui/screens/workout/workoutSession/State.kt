package com.example.fitnessfatality.ui.screens.workout.workoutSession

import androidx.lifecycle.MutableLiveData
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo

class State {
    val setIndex = MutableLiveData(1)
    val exerciseIndex = MutableLiveData(0)

    fun getSetIndex(): Int {
        return setIndex.value!!
    }

    fun getExerciseIndex(): Int {
        return exerciseIndex.value!!
    }

    private fun incrementExerciseIndex(): Int {
        exerciseIndex.value = exerciseIndex.value!!.plus(1)

        return exerciseIndex.value!!
    }

    fun incrementSetIndex(): Int {
        setIndex.value = setIndex.value!!.plus(1)

        return setIndex.value!!
    }

    fun moveToNextExercise() {
        incrementExerciseIndex()
        resetSetIndex()
    }

    fun resetState() {
        resetExerciseIndex()
        resetSetIndex()
    }

    private fun resetSetIndex(): Int {
        setIndex.value = 1

        return setIndex.value!!
    }

    private fun resetExerciseIndex(): Int {
        exerciseIndex.value = 0

        return exerciseIndex.value!!
    }

    fun isEndOfSession(exercises: List<RoutineExercisePojo>): Boolean {
        val currentExerciseNumberOfSets =
            exercises[getExerciseIndex()]
                .routineExercise!!
                .loggingParameters["sets"]!!.toInt()

        val isLastExercise = (exercises.size - 1) == getExerciseIndex()
        val isLastSetCompleted = currentExerciseNumberOfSets == getSetIndex()

        return isLastExercise && isLastSetCompleted
    }

    fun isLastSet(exercises: List<RoutineExercisePojo>): Boolean {
        val setsTarget =
            exercises[getExerciseIndex()]
                .routineExercise!!
                .loggingParameters["sets"]!!.toInt()

        return getSetIndex() >= setsTarget
    }


    override fun toString(): String {
        return "State(setIndex=${setIndex.value!!}, exerciseIndex=${exerciseIndex.value!!})"
    }
}
