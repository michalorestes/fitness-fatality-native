package com.example.fitnessfatality.ui.screens.workoutTracking.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.models.workout.WorkoutExercise
import com.example.fitnessfatality.data.repository.WorkoutExerciseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WorkoutExerciseViewModel(application: Application): AndroidViewModel(application) {
    val allWorkoutExercises: LiveData<List<WorkoutExercisePojo>>

    private val repository: WorkoutExerciseRepository

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val dao  = AppDatabase.getDatabase(application, scope).workoutExerciseDao()
        repository = WorkoutExerciseRepository(dao)
        allWorkoutExercises = repository.allWorkoutExercises
    }

    fun insert(workoutExercise: WorkoutExercise) = scope.launch(Dispatchers.IO){
        repository.insert(workoutExercise)
    }
}