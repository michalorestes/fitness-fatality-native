package com.example.fitnessfatality.data.models.workoutSession

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutAndLogs(
    @Embedded
    val workout: Workout,
    @Relation(parentColumn = "id", entityColumn = "workoutId")
    val logs: List<Log>
)