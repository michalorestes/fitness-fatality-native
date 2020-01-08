package com.example.fitnessfatality.data.models.workoutSession

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fitnessfatality.data.models.workout.Workout
import java.time.LocalDateTime

@Entity(
    tableName = "workout_session",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workout_id"]
        )
    ]
)
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "workout_id")
    val workoutId: Int,
    @ColumnInfo(name = "start_date")
    val startDate: LocalDateTime,
    @ColumnInfo(name = "end_date")
    val endDate: LocalDateTime
)
