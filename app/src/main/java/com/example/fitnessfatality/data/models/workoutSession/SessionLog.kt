package com.example.fitnessfatality.data.models.workoutSession

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
        ),
        ForeignKey(
            entity = WorkoutSession::class,
            parentColumns = ["id"],
            childColumns = ["session_id"]
        )
    ]
)
data class SessionLog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,
    @ColumnInfo(name = "session_id")
    val sessionId: Long,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Int,
    @ColumnInfo(name = "workout_id")
    val workoutId: Int,
    @ColumnInfo(name = "workout_exercise_id")
    val workoutExerciseId: Int,
    val type: LoggingType,
    val value: HashMap<Int, HashMap<LocalDateTime, HashMap<String, String>>>
)
