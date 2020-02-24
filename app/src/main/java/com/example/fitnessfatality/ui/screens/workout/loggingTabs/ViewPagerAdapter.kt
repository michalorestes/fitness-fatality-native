package com.example.fitnessfatality.ui.screens.workout.loggingTabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.ui.screens.workout.interfaces.SingleExerciseLogProvider

class ViewPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var data = listOf<RoutineExercisePojo>()
    private var pages = mutableMapOf<Int, SingleExerciseLogFragment>()

    override fun getItem(position: Int): Fragment {
        if (!pages.containsKey(position)) {
            pages[position] =
                SingleExerciseLogFragment(
                    data[position],
                    position
                )
        }

        return pages[position]!!
    }

    fun getLogProvider(position: Int): SingleExerciseLogProvider {
        if (!pages.containsKey(position)) {
            pages[position] =
                SingleExerciseLogFragment(
                    data[position],
                    position
                )
        }

        return pages[position]!!
    }

    override fun getCount(): Int = data.size

    fun setData(data: List<RoutineExercisePojo>) {
        this.data = data
        this.notifyDataSetChanged()
    }
}
