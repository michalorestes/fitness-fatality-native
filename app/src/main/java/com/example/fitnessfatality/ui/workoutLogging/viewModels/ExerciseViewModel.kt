package com.example.fitnessfatality.ui.workoutLogging.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.Exercise
import com.example.fitnessfatality.data.repository.ExerciseRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    val allExercises: LiveData<List<Exercise>>

    private var parentJob = Job()
    private val repository: ExerciseRepository
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val dao = AppDatabase.getDatabase(application, scope).exerciseDao()
        repository = ExerciseRepository(dao)
        allExercises = repository.allExercises
    }

    fun insert(exercise: Exercise) = scope.launch(Dispatchers.IO) {
        repository.insert(exercise)
    }
}