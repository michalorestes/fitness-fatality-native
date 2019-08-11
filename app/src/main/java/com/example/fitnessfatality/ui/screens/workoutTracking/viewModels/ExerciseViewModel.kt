package com.example.fitnessfatality.ui.screens.workoutTracking.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.repository.ExerciseRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    val allExercises: LiveData<List<Exercise>>

    private val repository: ExerciseRepository
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val dao = AppDatabase.getDatabase(application, scope).exerciseDao()
        repository = ExerciseRepository(dao)
        allExercises = repository.selectAllExercises()
    }

    fun insert(exercise: Exercise) = scope.launch(Dispatchers.IO) {
        repository.insert(exercise)
    }
}