package com.example.fitnessfatality.data.models.workoutSession

data class Rep(
    val id: Long,
    val setId: Long,
    val repIndex: Int,
    val value: Double,
    val unit: RepUnit
)
