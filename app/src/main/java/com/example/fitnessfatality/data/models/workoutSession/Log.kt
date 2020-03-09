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
    var id: Long? = null,
    var workoutId: Long? = null,
    var routineExerciseId: Long? = null,
    var exerciseId: Long? = null
) {
    @Ignore
    val logSets: ArrayList<LogSet> = arrayListOf()
}
