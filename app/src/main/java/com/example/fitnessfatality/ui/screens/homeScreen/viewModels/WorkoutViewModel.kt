package com.example.fitnessfatality.ui.screens.homeScreen.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.routine.Routine
import com.example.fitnessfatality.data.repository.RoutinesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WorkoutViewModel(application: Application): AndroidViewModel(application) {
    val allWorkouts: LiveData<List<Routine>>
    private val routinesRepository: RoutinesRepository

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val dao = AppDatabase.getDatabase(application, scope).workoutDao()
        routinesRepository = RoutinesRepository(dao)
        allWorkouts = routinesRepository.allWorkouts
    }

    fun insertWorkout(routine: Routine) = scope.launch(Dispatchers.IO) {
        routinesRepository.insert(routine)
    }
}
