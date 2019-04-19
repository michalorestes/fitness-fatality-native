package com.example.fitnessfatality.data.models.pojo

import androidx.room.Embedded
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.workout.WorkoutExercise

class WorkoutExercisePojo {
    @Embedded
    var workoutExercise: WorkoutExercise? = null

    @Embedded(prefix = "_")
    var exercise: Exercise? = null
}