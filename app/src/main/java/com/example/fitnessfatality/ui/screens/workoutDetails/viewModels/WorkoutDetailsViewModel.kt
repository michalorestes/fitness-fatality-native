package com.example.fitnessfatality.ui.screens.workoutDetails.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.models.workout.WorkoutExercise
import com.example.fitnessfatality.data.repository.WorkoutExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkoutDetailsViewModel(applicatin: Application): BaseViewModel(applicatin) {
    private val workoutExerciseRepository: WorkoutExerciseRepository

    var isRecyclerViewInEditMode: Boolean = false

    init {
        val db = AppDatabase.getDatabase(applicatin, scope)
        workoutExerciseRepository = WorkoutExerciseRepository(db.workoutExerciseDao())
    }

    fun findWorkoutExercisesByWorkoutId(workoutId: Int): LiveData<List<WorkoutExercisePojo>> {
        return workoutExerciseRepository.findWorkoutExercisesByWorkoutId(workoutId)
    }

    fun update(workoutExercise: WorkoutExercise) = scope.launch(Dispatchers.IO) {
        workoutExerciseRepository.update(workoutExercise)
    }

    fun delete(workoutExercise: WorkoutExercise) = scope.launch(Dispatchers.IO) {
        workoutExerciseRepository.delete(workoutExercise)
    }
}