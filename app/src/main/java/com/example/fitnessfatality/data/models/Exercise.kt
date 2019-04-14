package com.example.fitnessfatality.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val name: String,
    val exerciseType: String,
    val primaryMuscleGroup: String,
    val secondaryMuscleGroups: String,
    val isCustom: Boolean,
    val availableLoggingTypes: String,
    val selectedLoggingType: String
)
