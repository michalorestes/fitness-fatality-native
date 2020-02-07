package com.example.fitnessfatality.ui.screens.homeScreen.adapters

import android.view.View
import com.example.fitnessfatality.data.models.routine.Routine

interface OnWorkoutListItemClickListener {
    fun onWorkoutSelected(view: View, routine: Routine)
    fun onWorkoutSessionSelected(view: View, routine: Routine)
}