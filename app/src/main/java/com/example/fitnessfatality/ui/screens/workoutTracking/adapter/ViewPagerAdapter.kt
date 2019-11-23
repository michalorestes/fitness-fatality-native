package com.example.fitnessfatality.ui.screens.workoutTracking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.ui.screens.workoutTracking.SingleExerciseLogFragment

class ViewPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var data = listOf<WorkoutExercisePojo>()
    private var pages = mutableMapOf<Int, SingleExerciseLogFragment>()

    override fun getItem(position: Int): Fragment {
        if (!pages.containsKey(position)) {
            pages[position] = SingleExerciseLogFragment(data[position], position)
        }

        return pages[position]!!
    }

    override fun getCount(): Int = data.size

    fun setData(data: List<WorkoutExercisePojo>) {
        this.data = data
        this.notifyDataSetChanged()
    }
}
