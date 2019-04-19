package com.example.fitnessfatality.data.models.workout

import androidx.room.*
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.logging.LoggingType

@Entity(
    tableName = "workout_exercise",
    foreignKeys= [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"]
        )
    ]
)
data class WorkoutExercise(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val workoutId: Int,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Int,
    val sequenceOrder: Int,
    val loggingTarget: HashMap<String, Int>,
    val selectedLoggingType: LoggingType
) {
    override fun toString(): String {
        return "$id, $loggingTarget"
    }
}
