package com.example.fitnessfatality.ui.workoutDetails.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.repository.WorkoutExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel

class WorkoutDetailsViewModel(applicatin: Application): BaseViewModel(applicatin) {
    private val workoutExerciseRepository: WorkoutExerciseRepository

    init {
        val db = AppDatabase.getDatabase(applicatin, scope)
        workoutExerciseRepository = WorkoutExerciseRepository(db.workoutExerciseDao())
    }

    fun findWorkoutExercisesByWorkoutId(workoutId: Int): LiveData<List<WorkoutExercisePojo>> {
        return workoutExerciseRepository.findWorkoutExercisesByWorkoutId(workoutId)
    }
}