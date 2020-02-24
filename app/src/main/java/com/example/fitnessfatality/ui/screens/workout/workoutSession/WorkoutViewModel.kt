package com.example.fitnessfatality.ui.screens.workout.workoutSession

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.repository.WorkoutRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import com.example.fitnessfatality.ui.screens.workout.interfaces.UiController

class WorkoutViewModel(
    application: Application
) : BaseViewModel(application) {

    private var workoutManager: WorkoutManager? = null
    private val workoutRepository: WorkoutRepository

    init {
        val db = AppDatabase.getDatabase(application, scope)
        workoutRepository =
            WorkoutRepository(db.routineExerciseDao(), db.logDao(), db.logSetDao(), db.workoutDao())
    }

    fun initialiseWorkoutManager(routineId: Long, uiController: UiController) {
        val timer = Timer()
        val state = State()
        workoutManager = WorkoutManager(
            routineId,
            timer,
            state,
            uiController,
            workoutRepository
        )
    }

    fun onNextClick() {
        workoutManager!!.next()
    }

    fun getState(): State {
        return workoutManager!!.state
    }

    fun getExercises(): LiveData<List<RoutineExercisePojo>> {
        return workoutManager!!.getExercises()
    }
}
