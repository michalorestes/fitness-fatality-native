package com.example.fitnessfatality.ui.screens.routineDetails.exerciseDatabase.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.routine.RoutineExercise
import com.example.fitnessfatality.data.repository.ExerciseRepository
import com.example.fitnessfatality.data.repository.RoutineExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExercisesViewModel(applicatin: Application): BaseViewModel(applicatin) {
    private val exerciseRepository: ExerciseRepository
    val exercises: LiveData<List<Exercise>>

    private val routineExerciseRepository: RoutineExerciseRepository

    init {
        val db = AppDatabase.getDatabase(applicatin, scope)
        exerciseRepository = ExerciseRepository(db.exerciseDao())
        routineExerciseRepository = RoutineExerciseRepository(db.routineExerciseDao())
        exercises = exerciseRepository.selectAllExercises()
    }

    fun addExerciseToWorkout(exercise: Exercise, workoutId: Long) = scope.launch(Dispatchers.IO) {
        val workoutExercise = RoutineExercise(
            routineId = workoutId,
            exerciseId = exercise.id,
            loggingParameters = hashMapOf("sets" to 2, "rest" to 90, "reps" to 4) //TODO: This is some dummy data
        )

        routineExerciseRepository.insert(workoutExercise)
    }
}
