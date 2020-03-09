package com.example.fitnessfatality.data.models.pojo

import androidx.room.Embedded
import com.example.fitnessfatality.data.models.workoutSession.Log
import com.example.fitnessfatality.data.models.workoutSession.LogSet

class LogSetPojo {
    @Embedded
    var log: Log? = null

    @Embedded(prefix = "_")
    var set: LogSet? = null
}
