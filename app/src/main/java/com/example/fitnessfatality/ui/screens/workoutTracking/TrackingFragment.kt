package com.example.fitnessfatality.ui.screens.workoutTracking

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.workout.WorkoutExercise
import com.example.fitnessfatality.databinding.FragmentWorkoutLoggingBinding
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarAdapter
import com.example.fitnessfatality.ui.screens.mainActivity.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.screens.workoutTracking.viewModels.TrackingViewModel
import kotlinx.android.synthetic.main.fragment_workout_logging.view.*
import kotlinx.android.synthetic.main.fragment_workout_logging_log_entry.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TrackingFragment : Fragment() {
    private val args: TrackingFragmentArgs by navArgs()
    private lateinit var trackingViewModel: TrackingViewModel
    private lateinit var onActivityInteractionInterface: OnActivityInteractionInterface
    private lateinit var setsEntryContainer: LinearLayout
    private var setsEntriesContainers: ArrayList<LinearLayout> = arrayListOf()

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
        trackingViewModel = ViewModelProviders.of(this).get(TrackingViewModel::class.java)
        GlobalScope.launch {trackingViewModel.loadWorkoutExercises(args.workoutId)}
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

        setsEntryContainer = binding.root.sets_entry_container

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        this.onActivityInteractionInterface.setBottomAppBarAdapter(BottomAppBarAdapter(
            isGone = true
        ))

        trackingViewModel.currentExercise.observe(this, Observer {
            if (it !== null) {
                initialiseSetsEntriesContainer(it.workoutExercise)
            }
        })

        trackingViewModel.currentSet.observe(this, Observer { setIndex ->
            if (setIndex != 0) {
                setsEntriesContainers[setIndex - 1].txt_sets_count.setTextColor(
                    ContextCompat.getColor(context!!, R.color.colorBlack)
                )
            }

            if (setsEntriesContainers.isNotEmpty()) {
                setsEntriesContainers[setIndex].txt_sets_count.setTextColor(
                    ContextCompat.getColor(context!!, R.color.colorAccent)
                )
            }
        })

        trackingViewModel.isEndOfSession.observe(this, Observer {
            if (it == true) {
                Toast.makeText(context, "Session Completed!", Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun initialiseSetsEntriesContainer(workoutExercise: WorkoutExercise?) {
        if (workoutExercise == null) {
            return
        }

        val loggingTarget = workoutExercise.loggingTarget
        val setsNo = loggingTarget["sets"]!!
        val reps = loggingTarget["reps"]!!
        setsEntriesContainers.clear()
        setsEntryContainer.removeAllViews()
        for (rep in 1..setsNo) {
            val container = layoutInflater.inflate(
                R.layout.fragment_workout_logging_log_entry,
                null,
                false
            ) as LinearLayout
            container.edit_text_reps_number.hint = reps.toString()
            container.edit_text_weight_lifed.hint = 0.toString()
            container.txt_sets_count.text = rep.toString()
            setsEntriesContainers.add(container)
            setsEntryContainer.addView(container)
        }

        if (setsEntriesContainers.isNotEmpty()) {
            setsEntriesContainers[trackingViewModel.currentSet.value!!]
                .txt_sets_count
                .setTextColor(
                    ContextCompat.getColor(context!!, R.color.colorAccent)
                )
        }
    }
}
