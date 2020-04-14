package com.example.fitnessfatality.data.models.routine

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine")
data class Routine(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long? = null,
    val name: String,
    val workoutIcon: Int,
    var sequenceId: Int = 0
)
