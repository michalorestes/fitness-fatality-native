package com.example.fitnessfatality.ui.exerciseDatabase.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.logging.LoggingType
import com.example.fitnessfatality.data.models.workout.WorkoutExercise
import com.example.fitnessfatality.data.repository.ExerciseRepository
import com.example.fitnessfatality.data.repository.WorkoutExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExercisesViewModel(applicatin: Application): BaseViewModel(applicatin) {
    private val exerciseRepository: ExerciseRepository
    val exercises: LiveData<List<Exercise>>

    private val workoutExerciseRepository: WorkoutExerciseRepository

    init {
        val db = AppDatabase.getDatabase(applicatin, scope)
        exerciseRepository = ExerciseRepository(db.exerciseDao())
        exercises = exerciseRepository.selectAllExercises()

        workoutExerciseRepository = WorkoutExerciseRepository(db.workoutExerciseDao())
    }

    fun addExerciseToWorkout(exercise: Exercise, workoutId: Int) = scope.launch(Dispatchers.IO) {
        val workoutExercise = WorkoutExercise(
            workoutId = workoutId,
            exerciseId = exercise.id,
            selectedLoggingType = LoggingType.WEIGHTS
        )

        workoutExerciseRepository.insert(workoutExercise)
    }
}
