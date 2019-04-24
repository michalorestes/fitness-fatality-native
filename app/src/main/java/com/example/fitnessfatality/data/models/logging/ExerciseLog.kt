package com.example.fitnessfatality.data.models.logging

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.workout.WorkoutExercise
import java.time.LocalDateTime

@Entity(
    tableName = "exercise_log",
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"]
        ),
        ForeignKey(
            entity = WorkoutExercise::class,
            parentColumns = ["id"],
            childColumns = ["workout_exercise_id"]
        )
    ]
)
data class ExerciseLog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    val date: LocalDateTime,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Int,
    @ColumnInfo(name = "workout_id")
    val workoutId: Int,
    @ColumnInfo(name = "workout_exercise_id")
    val workoutExerciseId: Int,
    val type: LoggingType,
    val value: Map<String, String>
)
