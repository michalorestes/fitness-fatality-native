package com.example.fitnessfatality.data.models.routine

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine")
data class Routine(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    val name: String,
    val workoutIcon: Int
)
