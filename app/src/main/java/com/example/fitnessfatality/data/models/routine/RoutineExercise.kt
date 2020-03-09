package com.example.fitnessfatality.data.models.routine

import androidx.room.*
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.workoutSession.Log
import com.example.fitnessfatality.data.models.workoutSession.LoggingType

@Entity(
    tableName = "routine_exercise",
    foreignKeys= [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"]
        )
    ]
)
data class RoutineExercise(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long? = null,
    val routineId: Long,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Long,
    val sequenceOrder: Int = 0,
    val loggingParameters: HashMap<String, Int> = hashMapOf()
) {
    @Ignore
    var exerciseLogs: Log? = null

    override fun toString(): String {
        return "$id, $loggingParameters"
    }
}
