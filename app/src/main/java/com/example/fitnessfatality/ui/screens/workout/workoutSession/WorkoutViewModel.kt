package com.example.fitnessfatality.ui.screens.workout.workoutSession

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.repository.WorkoutRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import com.example.fitnessfatality.ui.screens.workout.interfaces.UiControler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorkoutViewModel(
    application: Application
): BaseViewModel(application) {

    private var workoutManager: WorkoutManager? = null
    private val workoutRepository: WorkoutRepository

    init {
        val db = AppDatabase.getDatabase(application, scope)
        workoutRepository = WorkoutRepository(db.routineExerciseDao())
    }

    fun initialiseWorkoutManager(routineId: Int, uiController: UiControler) {
        val timer = Timer()
        val state = State()
        workoutManager = WorkoutManager(
            routineId,
            timer,
            state
        )

        GlobalScope.launch(Dispatchers.Main) {
            val routines = withContext(Dispatchers.Default) {
                workoutRepository.fetchRoutineExecises(routineId)
            }

            workoutManager!!.setExercises(routines)

            uiController.initialiseViewPager(workoutManager!!.getExercises().value!!)
        }
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
