package com.example.fitnessfatality.data.models.workoutSession

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "log_set",
    foreignKeys = [
        ForeignKey(
            entity = Log::class,
            parentColumns = ["id"],
            childColumns = ["logId"]
        )
    ]
)
data class LogSet(
    @PrimaryKey
    var id: Long? = null,
    var logId: Long? = null,
    var setIndex: Int,
    var numberOfReps: Int?,
    var weightLifted: Double?
)
