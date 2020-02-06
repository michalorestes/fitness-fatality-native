package com.example.fitnessfatality.ui.screens.workoutTracking

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.databinding.FragmentWorkoutLoggingBinding
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarAdapter
import com.example.fitnessfatality.ui.screens.mainActivity.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.screens.workoutTracking.adapter.ViewPagerAdapter
import com.example.fitnessfatality.ui.screens.workoutTracking.customViews.TrackingViewPager
import com.example.fitnessfatality.ui.screens.workoutTracking.interfaces.ViewPagerPage
import com.example.fitnessfatality.ui.screens.workoutTracking.viewModels.TrackingViewModel
import kotlinx.android.synthetic.main.fragment_logging_bottomsheet_exercise_entry.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TrackingFragment : Fragment(), TrackingViewModel.FragmentProvider {
    private val args: TrackingFragmentArgs by navArgs()
    private lateinit var trackingViewModel: TrackingViewModel
    private lateinit var onActivityInteractionInterface: OnActivityInteractionInterface
    private lateinit var viewPager: TrackingViewPager
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var bottomSheetLayoutContainer: LinearLayout

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
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        trackingViewModel = ViewModelProviders.of(this).get(TrackingViewModel::class.java)
        trackingViewModel.setViewPagerProvider(this)
        GlobalScope.launch {
            trackingViewModel.initialiseWorkoutSession(args.workoutId)
        }
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
        binding.trackingViewModel = trackingViewModel

        viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter
        bottomSheetLayoutContainer = binding.exercisesContainer

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

        trackingViewModel.sessionLogRepository.selectAll().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Log.d("-->", it.toString())
            }
        })
    }

    override fun getCurrentPage(): ViewPagerPage {
        return viewPagerAdapter.getItem(viewPager.currentItem) as ViewPagerPage
    }

    override fun displayNextPage() {
        viewPager.setCurrentItem(viewPager.currentItem.plus(1))
    }

    override fun setAdapterData(workoutExercises: List<WorkoutExercisePojo>) {
        viewPagerAdapter.setData(workoutExercises)
    }

    override fun updateBottomSheetUi(workoutExercises: List<WorkoutExercisePojo>) {
        workoutExercises.forEach {
            val layout = layoutInflater.inflate(
                R.layout.fragment_logging_bottomsheet_exercise_entry,
                bottomSheetLayoutContainer,
                false
            )

            layout.lbl_exercise_name.setText(it.exercise!!.name)
            bottomSheetLayoutContainer.addView(layout)
        }
    }
}
