package com.example.fitnessfatality.ui.screens.workoutTracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.fitnessfatality.R
import com.example.fitnessfatality.databinding.FragmentWorkoutLoggingBinding
import com.example.fitnessfatality.ui.screens.workoutTracking.viewModels.TrackingViewModel

class TrackingFragment : Fragment() {
    val args: TrackingFragmentArgs by navArgs()

    private lateinit var trackingViewModel: TrackingViewModel
    private var logs: MutableLiveData<String> = MutableLiveData()
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        trackingViewModel = ViewModelProviders.of(this).get(TrackingViewModel::class.java)
        trackingViewModel.loadWorkoutExercises(args.workoutId)
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
        binding.viewModel = trackingViewModel
        binding.handler = this
        binding.logs = logs

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        trackingViewModel
            .getWorkoutWorkoutExercises()
            .observe(this, Observer {
                if (!it.isNullOrEmpty()) {
                    trackingViewModel.updateUiData()
                }
        })

        trackingViewModel.getLogs().observe(this, Observer {
            if (it != null) {
                logs.value = ""
                it.forEach { log ->
                    logs.value = logs.value + log.toString() + "\n \n"
                }
            }
        })
    }

    fun onNext(view: View) {
        trackingViewModel.onNextHandler(view)
    }
}
