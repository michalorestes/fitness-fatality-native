package com.example.fitnessfatality.data.models.workoutSession

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "set",
    foreignKeys = [
        ForeignKey(
            entity = Log::class,
            parentColumns = ["id"],
            childColumns = ["logId"]
        )
    ]
)
data class Set(
    @PrimaryKey
    val id: Long,
    val logId: Long,
    val setIndex: Int,
    val reps: List<Rep>
)
