package com.example.fitnessfatality.ui.screens.workout.workoutSession

import androidx.lifecycle.MutableLiveData
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import java.lang.Exception

class State {
    var setIndex = MutableLiveData(1)
    var exerciseIndex = MutableLiveData(0)

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
        exerciseIndex = MutableLiveData(0)

        return exerciseIndex.value!!
    }

    fun isEndOfSession(exercises: List<RoutineExercisePojo>): Boolean {
        return try {
            val currentExerciseNumberOfSets =
                exercises[getExerciseIndex()]
                    .routineExercise!!
                    .loggingParameters["sets"]!!.toInt()

            val isLastExercise = (exercises.size - 1) < getExerciseIndex()
            val isLastSetCompleted = currentExerciseNumberOfSets == getSetIndex()

            isLastExercise && isLastSetCompleted
        } catch (exception: Exception) {
            true
        }
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
