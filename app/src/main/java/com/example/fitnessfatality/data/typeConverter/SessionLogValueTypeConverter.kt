package com.example.fitnessfatality.data.typeConverter

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

class SessionLogValueTypeConverter {
    @TypeConverter
    fun valueToJson(value: HashMap<Int, HashMap<LocalDateTime, HashMap<String, String>>>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToValue(json: String): HashMap<Int, HashMap<LocalDateTime, HashMap<String, String>>> {
        Log.d("??", json)
        return Gson().fromJson(
            json,
            object: TypeToken<HashMap<Int, HashMap<LocalDateTime, HashMap<String, String>>>>() {}.type
        )
    }
}
