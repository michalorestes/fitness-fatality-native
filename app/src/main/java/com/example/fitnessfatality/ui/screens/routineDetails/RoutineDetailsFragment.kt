package com.example.fitnessfatality.ui.screens.routineDetails

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
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.models.routine.RoutineExercise
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarActionListenerInterface
import com.example.fitnessfatality.ui.customViews.customBottomAppBar.BottomAppBarAdapter
import com.example.fitnessfatality.ui.screens.mainActivity.OnActivityInteractionInterface
import com.example.fitnessfatality.ui.screens.routineDetails.adapters.OnRoutineExerciseClickListener
import com.example.fitnessfatality.ui.screens.routineDetails.adapters.RoutineExercisesListAdapter
import com.example.fitnessfatality.ui.screens.routineDetails.viewModels.RoutineDetailsViewModel
import kotlinx.android.synthetic.main.fragment_workout_details.*

class RoutineDetailsFragment : Fragment(), BottomAppBarActionListenerInterface,
    OnRoutineExerciseClickListener {

    private val args: RoutineDetailsFragmentArgs by navArgs()
    private lateinit var recyclerViewAdapter: RoutineExercisesListAdapter
    private lateinit var routineDetailsViewModel: RoutineDetailsViewModel
    private lateinit var onActivityInteractionInterface: OnActivityInteractionInterface

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
        routineDetailsViewModel =
            ViewModelProviders.of(activity!!).get(RoutineDetailsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_workout_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = RoutineExercisesListAdapter(
            this,
            routineDetailsViewModel.isRecyclerViewInEditMode
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

        routineDetailsViewModel
            .findWorkoutExercisesByWorkoutId(args.workoutId)
            .observe(this, Observer {
                if (it != null) {
                    recyclerViewAdapter.updateDataSet(it as ArrayList<RoutineExercisePojo>)
                }
            })
    }

    override fun onPause() {
        super.onPause()
        routineDetailsViewModel.isRecyclerViewInEditMode = false
        recyclerViewAdapter.isRecyclerViewInEditMode =
            routineDetailsViewModel.isRecyclerViewInEditMode
    }

    override fun onFloatingActionButtonPress(view: View) {
        val action = RoutineDetailsFragmentDirections.actionToTrackWorkout(args.workoutId)
        Navigation
            .findNavController(activity!!, R.id.workout_nav_host_fragment)
            .navigate(action)
    }

    override fun onOptionsMenu(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_exercise_database -> {
                val action =
                    RoutineDetailsFragmentDirections.actionToExerciseDatabase(args.workoutId)
                Navigation
                    .findNavController(activity!!, R.id.workout_nav_host_fragment)
                    .navigate(action)
            }
            R.id.nav_exercise_edit -> {
                routineDetailsViewModel.isRecyclerViewInEditMode =
                    !routineDetailsViewModel.isRecyclerViewInEditMode

                recyclerViewAdapter.isRecyclerViewInEditMode =
                    routineDetailsViewModel.isRecyclerViewInEditMode
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }

        return true
    }

    override fun onWorkoutExerciseInfoClick(routineExercise: RoutineExercisePojo) {
        val dialog = RoutineExerciseInfoDialog(routineExercise, routineDetailsViewModel)
        dialog.show(fragmentManager!!, "my_dialog")
    }

    override fun onWorkoutExerciseDeleteClick(routineExercise: RoutineExercise) {
        routineDetailsViewModel.delete(routineExercise)
    }
}
