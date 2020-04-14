package com.example.fitnessfatality.ui.screens.homeScreen.myRoutines

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.routine.Routine
import com.example.fitnessfatality.data.repository.RoutinesRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoutinesViewModel(application: Application): BaseViewModel(application) {
    val allWorkouts: LiveData<List<Routine>>
    var isInEditMode = MutableLiveData(false)
    private val routinesRepository: RoutinesRepository

    init {
        val dao = AppDatabase.getDatabase(application, scope).routineDao()
        routinesRepository = RoutinesRepository(dao)
        allWorkouts = routinesRepository.allWorkouts
    }

    fun insertWorkout(routine: Routine) = scope.launch(Dispatchers.IO) {
        val numberOfRoutines = routinesRepository.getNumberOfRoutines()
        routine.sequenceId = numberOfRoutines.plus(1)
        routinesRepository.insert(routine)
    }

    fun deleteRoutine(routine: Routine) = scope.launch(Dispatchers.IO) {
        routinesRepository.deleteRoutine(routine)
    }

    fun batchUpdateRoutines(routines: List<Routine>) = scope.launch(Dispatchers.IO) {
        routinesRepository.batchUpdate(routines)
    }
}
