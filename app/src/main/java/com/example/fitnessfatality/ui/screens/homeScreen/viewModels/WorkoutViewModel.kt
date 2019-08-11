package com.example.fitnessfatality.ui.screens.homeScreen.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.workout.Workout
import com.example.fitnessfatality.data.repository.WorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WorkoutViewModel(application: Application): AndroidViewModel(application) {
    val allWorkouts: LiveData<List<Workout>>
    private val workoutRepository: WorkoutRepository

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val dao = AppDatabase.getDatabase(application, scope).workoutDao()
        workoutRepository = WorkoutRepository(dao)
        allWorkouts = workoutRepository.allWorkouts
    }

    fun insertWorkout(workout: Workout) = scope.launch(Dispatchers.IO) {
        workoutRepository.insert(workout)
    }
}
