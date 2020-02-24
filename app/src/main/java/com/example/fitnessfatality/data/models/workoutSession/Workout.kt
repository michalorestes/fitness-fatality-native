package com.example.fitnessfatality.data.models.workoutSession

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fitnessfatality.data.models.routine.Routine
import java.time.LocalDateTime

@Entity(
    tableName = "workout",
    foreignKeys = [
        ForeignKey(
            entity = Routine::class,
            parentColumns = ["id"],
            childColumns = ["routineId"]
        )
    ]
)
data class Workout(
    @PrimaryKey
    var id: Long?,
    var routineId: Long?,
    var startTime: LocalDateTime?,
    var endTime: LocalDateTime?
)