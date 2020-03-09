package com.example.fitnessfatality.data.models.pojo

import androidx.room.Embedded
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.routine.RoutineExercise

class RoutineExercisePojo {
    @Embedded
    var routineExercise: RoutineExercise? = null

    @Embedded(prefix = "_")
    var exercise: Exercise? = null
}
