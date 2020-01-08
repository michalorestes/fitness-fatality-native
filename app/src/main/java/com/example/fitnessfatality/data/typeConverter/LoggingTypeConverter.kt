package com.example.fitnessfatality.data.typeConverter

import androidx.room.TypeConverter
import com.example.fitnessfatality.data.models.workoutSession.LoggingType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LoggingTypeConverter {
    @TypeConverter
    fun fromList(availableLoggingTypes: List<LoggingType>): String {
        return Gson().toJson(availableLoggingTypes)
    }

    @TypeConverter
    fun fromJson(availableLoggingTypesJSON: String): List<LoggingType> {
        return Gson().fromJson(availableLoggingTypesJSON, object : TypeToken<List<LoggingType>>() {}.type)
    }

    @TypeConverter
    fun loggingTypeToStr(loggingType: LoggingType): String {
        return loggingType.name
    }

    @TypeConverter
    fun fromStrToLoggingType(loggingTypeStr: String): LoggingType {
        return LoggingType.valueOf(loggingTypeStr)
    }

    @TypeConverter
    fun hashMapToJson(loggingTarget: HashMap<String, Int>): String {
        return Gson().toJson(loggingTarget)
    }

    @TypeConverter
    fun fromJsonToHashMap(loggingTarget: String): HashMap<String, Int> {
        return Gson().fromJson(loggingTarget, object: TypeToken<HashMap<String, Int>>() {}.type)
    }
}
