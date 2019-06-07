package com.example.fitnessfatality.ui.exerciseDatabase.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.repository.ExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel

class ExercisesViewModel(applicatin: Application): BaseViewModel(applicatin) {
    private val exerciseRepository: ExerciseRepository
    val exercises: LiveData<List<Exercise>>

    init {
        val db = AppDatabase.getDatabase(applicatin, scope)
        exerciseRepository = ExerciseRepository(db.exerciseDao())
        exercises = exerciseRepository.selectAllExercises()
    }
}
