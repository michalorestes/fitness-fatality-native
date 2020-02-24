package com.example.fitnessfatality.ui.screens.routineDetails.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.models.routine.RoutineExercise
import com.example.fitnessfatality.data.repository.RoutineExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoutineDetailsViewModel(applicatin: Application): BaseViewModel(applicatin) {
    private val routineExerciseRepository: RoutineExerciseRepository

    var isRecyclerViewInEditMode: Boolean = false

    init {
        val db = AppDatabase.getDatabase(applicatin, scope)
        routineExerciseRepository = RoutineExerciseRepository(db.routineExerciseDao())
    }

    fun findWorkoutExercisesByWorkoutId(workoutId: Long): LiveData<List<RoutineExercisePojo>> {
        return routineExerciseRepository.findWorkoutExercisesByWorkoutId(workoutId)
    }

    fun update(routineExercise: RoutineExercise) = scope.launch(Dispatchers.IO) {
        routineExerciseRepository.update(routineExercise)
    }

    fun delete(routineExercise: RoutineExercise) = scope.launch(Dispatchers.IO) {
        routineExerciseRepository.delete(routineExercise)
    }
}
