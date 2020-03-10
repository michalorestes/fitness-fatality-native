package com.example.fitnessfatality.data.typeConverter

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTypeConverter {

    @TypeConverter
    fun dateToString(date: LocalDateTime?): String {
        return date.toString()
    }

    @TypeConverter
    fun stringToDate(date: String): LocalDateTime {
        return LocalDateTime.parse(date)
    }
}
