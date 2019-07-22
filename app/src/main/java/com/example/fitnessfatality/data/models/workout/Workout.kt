package com.example.fitnessfatality.data.models.workout

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class Workout(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    val name: String,
    val workoutIcon: Int
)
