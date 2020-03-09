package com.example.fitnessfatality.ui.screens.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.pojo.LogSetPojo
import com.example.fitnessfatality.data.repository.ExerciseRepository
import com.example.fitnessfatality.data.repository.LogRepository
import com.example.fitnessfatality.ui.base.BaseViewModel

class DashboardViewModel(application: Application): BaseViewModel(application) {

    private val exerciseRepository: ExerciseRepository
    private val logRepository: LogRepository

    init {
        val db = AppDatabase.getDatabase(application, scope)
        exerciseRepository = ExerciseRepository(db.exerciseDao())
        logRepository = LogRepository(db.logDao())
    }

    fun fetchExercises(): LiveData<List<Exercise>> {
        return exerciseRepository.selectAllExercises()
    }

    fun fetchExerciseLogs(exerciseId: Long): LiveData<List<LogSetPojo>> {
        return logRepository.fetchLogsByExercise(exerciseId)
    }
}
