package com.example.fitnessfatality.data.models.exercise

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val name: String,
    val exerciseType: ExerciseType,
    val primaryMuscleGroup: MuscleGroup,
    val secondaryMuscleGroups: List<MuscleGroup>,
    val isCustom: Boolean
)
