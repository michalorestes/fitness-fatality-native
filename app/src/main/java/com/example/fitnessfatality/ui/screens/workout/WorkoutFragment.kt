package com.example.fitnessfatality.ui.screens.workout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.models.workoutSession.Log
import com.example.fitnessfatality.databinding.FragmentWorkoutLoggingBinding
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarAdapter
import com.example.fitnessfatality.ui.screens.mainActivity.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.screens.workout.loggingTabs.ViewPagerAdapter
import com.example.fitnessfatality.ui.screens.workout.customViews.WorkoutViewPager
import com.example.fitnessfatality.ui.screens.workout.interfaces.UiController
import com.example.fitnessfatality.ui.screens.workout.workoutSession.WorkoutViewModel

class WorkoutFragment : Fragment(), UiController {
    private val args: WorkoutFragmentArgs by navArgs()
    private lateinit var onActivityInteractionInterface: OnActivityInteractionInterface
    private lateinit var viewPager: WorkoutViewPager
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnActivityInteractionInterface) {
            onActivityInteractionInterface = context
        } else {
            throw Exception("MyWorkoutsFragments requires OnActivityInteractionInterface")
        }
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        viewPagerAdapter =
            ViewPagerAdapter(
                childFragmentManager
            )

        workoutViewModel = ViewModelProviders.of(this).get(WorkoutViewModel::class.java)
        workoutViewModel.initialiseWorkoutManager(args.workoutId, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentWorkoutLoggingBinding>(
                inflater,
                R.layout.fragment_workout_logging,
                container,
                false
            )

        binding.lifecycleOwner = this
        binding.viewModel = workoutViewModel

        viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        this.onActivityInteractionInterface.setBottomAppBarAdapter(
            BottomAppBarAdapter(
                isGone = true
            )
        )

        viewPager.setCurrentItem(1, true)

        workoutViewModel.getState().exerciseIndex.observe(this, Observer {
            if (it != null) {
                displayNextPage()
            }
        })

    }

    override fun initialiseViewPager(data: List<RoutineExercisePojo>) {
        this.viewPagerAdapter.setData(data)
    }

    override fun getExerciseLog(): Log {
        return viewPagerAdapter.getLogProvider(viewPager.currentItem).getLogValue()
    }

    override fun navigateToEndOfWorkout() {
        Navigation
            .findNavController(activity!!, R.id.workout_nav_host_fragment)
            .navigate(R.id.action_workoutLoggingFragment_to_trackingEndFragment)
    }

    private fun displayNextPage() {
        viewPager.currentItem = viewPager.currentItem.plus(1)
    }
}
