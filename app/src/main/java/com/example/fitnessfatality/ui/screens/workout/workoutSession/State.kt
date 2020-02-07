package com.example.fitnessfatality.ui.screens.workout.workoutSession

import androidx.lifecycle.MutableLiveData

class State {
    val setIndex = MutableLiveData(1)
    val exerciseIndex = MutableLiveData(0)

    fun getSetIndex(): Int {
        return setIndex.value!!
    }

    fun getExerciseIndex(): Int {
        return exerciseIndex.value!!
    }

    fun incrementExerciseIndex(): Int {
        exerciseIndex.value = exerciseIndex.value!!.plus(1)

        return exerciseIndex.value!!
    }

    fun incrementSetIndex(): Int {
        setIndex.value = setIndex.value!!.plus(1)

        return setIndex.value!!
    }

    fun resetSetIndex(): Int {
        setIndex.value = 1

        return setIndex.value!!
    }

    fun resetExerciseIndex(): Int {
        exerciseIndex.value = 0

        return exerciseIndex.value!!
    }

    override fun toString(): String {
        return "State(setIndex=${setIndex.value!!}, exerciseIndex=${exerciseIndex.value!!})"
    }
}
