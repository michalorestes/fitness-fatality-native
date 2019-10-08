package com.example.fitnessfatality.ui.screens.workoutDetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.models.workout.WorkoutExercise
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarActionListenerInterface
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarAdapter
import com.example.fitnessfatality.ui.screens.mainActivity.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.screens.workoutDetails.adapters.OnWorkoutExerciseClickListener
import com.example.fitnessfatality.ui.screens.workoutDetails.adapters.WorkoutExercisesListAdapter
import com.example.fitnessfatality.ui.screens.workoutDetails.viewModels.WorkoutDetailsViewModel
import kotlinx.android.synthetic.main.fragment_workout_details.*

class WorkoutDetailsFragment : Fragment(), BottomAppBarActionListenerInterface,
    OnWorkoutExerciseClickListener {

    private val args: WorkoutDetailsFragmentArgs by navArgs()
    private lateinit var onActivityInteractionInterface: OnActivityInteractionInterface
    private lateinit var recyclerViewAdapter: WorkoutExercisesListAdapter
    private lateinit var workoutDetailsViewModel: WorkoutDetailsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnActivityInteractionInterface) {
            onActivityInteractionInterface = context
        } else {
            throw Exception("MyWorkoutsFragments requires OnActivityInteractionInterface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        workoutDetailsViewModel =
            ViewModelProviders.of(activity!!).get(WorkoutDetailsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_workout_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = WorkoutExercisesListAdapter(
            this,
            workoutDetailsViewModel.isRecyclerViewInEditMode
        )
        val myLayoutManager = LinearLayoutManager(context)
        (workout_exercises_list as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = myLayoutManager
            adapter = recyclerViewAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        onActivityInteractionInterface.setBottomAppBarAdapter(
            BottomAppBarAdapter(
                isPrimaryBottomAppBar = false,
                navigationMenu = R.menu.workout_details_bottom_app_bar_menu,
                fabDrawableResourceId = R.drawable.ic_arrow_back_black_24dp,
                actionListenerInterface = this
            )
        )

        workoutDetailsViewModel
            .findWorkoutExercisesByWorkoutId(args.workoutId)
            .observe(this, Observer {
                if (it != null) {
                    recyclerViewAdapter.updateDataSet(it as ArrayList<WorkoutExercisePojo>)
                }
            })
    }

    override fun onPause() {
        super.onPause()
        workoutDetailsViewModel.isRecyclerViewInEditMode = false
        recyclerViewAdapter.isRecyclerViewInEditMode = workoutDetailsViewModel.isRecyclerViewInEditMode
    }

    override fun onFloatingActionButtonPress(view: View) {
        val action = WorkoutDetailsFragmentDirections.actionToTrackWorkout(args.workoutId)
        Navigation
            .findNavController(activity!!, R.id.workout_nav_host_fragment)
            .navigate(action)
    }

    override fun onOptionsMenu(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_exercise_database -> {
                val action =
                    WorkoutDetailsFragmentDirections.actionToExerciseDatabase(args.workoutId)
                Navigation
                    .findNavController(activity!!, R.id.workout_nav_host_fragment)
                    .navigate(action)
            }
            R.id.nav_exercise_edit -> {

                Log.d("-->", "Clicked edit button")
                workoutDetailsViewModel.isRecyclerViewInEditMode =
                    !workoutDetailsViewModel.isRecyclerViewInEditMode

                recyclerViewAdapter.isRecyclerViewInEditMode =
                    workoutDetailsViewModel.isRecyclerViewInEditMode
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }

        return true
    }

    override fun onWorkoutExerciseInfoClick(workoutExercise: WorkoutExercisePojo) {
        val dialog = WorkoutExerciseInfoDialog(workoutExercise, workoutDetailsViewModel)
        dialog.show(fragmentManager!!, "my_dialog")
    }

    override fun onWorkoutExerciseDeleteClick(workoutExercise: WorkoutExercise) {
        workoutDetailsViewModel.delete(workoutExercise)
    }
}
