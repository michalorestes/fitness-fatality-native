package com.example.fitnessfatality.ui.workoutTracking.viewModels

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.repository.WorkoutExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel

class TrackingViewModel(application: Application): BaseViewModel(application) {
    val workoutExercises: LiveData<List<WorkoutExercisePojo>>
    private val workoutExerciseRepository: WorkoutExerciseRepository

    val currentIndex: MutableLiveData<Int> = MutableLiveData()

    val index: LiveData<Int> = currentIndex

    init {
        val db = AppDatabase.getDatabase(application, scope)
        workoutExerciseRepository = WorkoutExerciseRepository(db.workoutExerciseDao())
        workoutExercises = workoutExerciseRepository.allWorkoutExercises
        currentIndex.value = 0
    }

    fun incrementIndex() {
        currentIndex.value = currentIndex.value!!.plus(1)
    }
}