package com.example.fitnessfatality.data.models.exercise

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnessfatality.data.models.logging.LoggingType
import com.example.fitnessfatality.data.models.workout.MuscleGroup

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val name: String,
    val exerciseType: ExerciseType,
    val primaryMuscleGroup: MuscleGroup,
    val secondaryMuscleGroups: List<MuscleGroup>,
    val isCustom: Boolean,
    val availableLoggingTypes: List<LoggingType>,
    val defaultLoggingType: LoggingType
)
