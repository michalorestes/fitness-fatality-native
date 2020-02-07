package com.example.fitnessfatality.ui.screens.workout.interfaces

import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo

interface UiControler {
    fun initialiseViewPager(data: List<RoutineExercisePojo>)
}