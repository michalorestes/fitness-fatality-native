package com.example.fitnessfatality.data.typeConverter

import androidx.room.TypeConverter
import com.example.fitnessfatality.data.models.exercise.ExerciseType
import com.example.fitnessfatality.data.models.exercise.MuscleGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExerciseTypeConverter {
    @TypeConverter
    fun muscleGroupToString(muscleGroup: MuscleGroup): String {
        return muscleGroup.name
    }

    @TypeConverter
    fun fromStringToMuscleGroup(muscleGroup: String): MuscleGroup {
        return MuscleGroup.valueOf(muscleGroup)
    }

    @TypeConverter
    fun fromListMuscleGroupsToJson(muscleGroups: List<MuscleGroup>): String {
        return Gson().toJson(muscleGroups)
    }

    @TypeConverter
    fun fromJsonToListMuscleGroups(muscleGroups: String): List<MuscleGroup> {
        return Gson().fromJson(muscleGroups, object: TypeToken<List<MuscleGroup>>() {}.type)
    }

    @TypeConverter
    fun exerciseTypeToString(exerciseType: ExerciseType): String {
        return exerciseType.name
    }

    @TypeConverter
    fun fromStringToExerciseType(exerciseType: String): ExerciseType {
        return ExerciseType.valueOf(exerciseType)
    }
}
