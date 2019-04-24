package com.example.fitnessfatality.data.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapTypeConverter {
    @TypeConverter
    fun mapToJson(map: Map<String, String>): String {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun jsonToMap(json: String): Map<String, String> {
        return Gson().fromJson(json, object: TypeToken<Map<String, String>>() {}.type)
    }
}
