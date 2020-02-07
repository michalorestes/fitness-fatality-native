package com.example.fitnessfatality.data.models.workoutSession

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(
    tableName = "log",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"]
        )
    ]
)
data class Log(
    @PrimaryKey
    val id: Long,
    val workoutId: Long,
    val routineExerciseId: Long,
    @Ignore
    val sets: ArrayList<Set>
)
