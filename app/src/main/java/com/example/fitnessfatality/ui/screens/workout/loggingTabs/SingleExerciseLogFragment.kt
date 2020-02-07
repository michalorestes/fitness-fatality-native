package com.example.fitnessfatality.ui.screens.workout.loggingTabs

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.pojo.RoutineExercisePojo
import com.example.fitnessfatality.data.models.routine.RoutineExercise
import com.example.fitnessfatality.ui.screens.workout.workoutSession.WorkoutViewModel
import kotlinx.android.synthetic.main.dialog_bottomsheet_workout_details.view.lbl_exercise_name
import kotlinx.android.synthetic.main.fragment_workout_logging_log_entry.view.*
import kotlinx.android.synthetic.main.view_pager_workout_logging.view.*

class SingleExerciseLogFragment(
    private val routineExercise: RoutineExercisePojo,
    private val position: Int
) : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutViewModel =
            ViewModelProviders.of(parentFragment!!).get(WorkoutViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.view_pager_workout_logging,
            container,
            false
        )

        setExerciseNameLabel(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        initialiseSetsEntriesContainerLayout(routineExercise.routineExercise!!)
        workoutViewModel.getState().setIndex.observe(this, Observer {
            val currentExerciseIndex = workoutViewModel.getState().exerciseIndex.value!!
            if (it != null && position == currentExerciseIndex) {
                highlightSet(it.minus(1))
            }
        })
    }

    private fun setExerciseNameLabel(view: View) {
        view.lbl_exercise_name.text = routineExercise.exercise!!.name
    }

    private fun initialiseSetsEntriesContainerLayout(routineExercise: RoutineExercise) {
        val loggingTarget = routineExercise.loggingParameters
        val numberOfSets = loggingTarget["sets"]!!
        val numberOfReps = loggingTarget["reps"]!!

        populateSetsContainerLayout(numberOfSets, numberOfReps)
    }

    private fun clearSetsContainerViews() {
        view!!.sets_entry_container.removeAllViews()
    }

    private fun populateSetsContainerLayout(setsCount: Int, numberOfReps: Int) {
        clearSetsContainerViews()
        val setsEntryContainer = view!!.sets_entry_container
        for (rep in 1..setsCount) {
            val container = inflateEntryContainer(setsEntryContainer)
            container.edit_text_reps_number.hint = numberOfReps.toString()
            container.edit_text_weight_lifed.hint = 0.toString()
            container.txt_sets_count.text = rep.toString()
            setsEntryContainer.addView(container)
        }
    }

    private fun inflateEntryContainer(container: LinearLayout): LinearLayout {
        return layoutInflater.inflate(
            R.layout.fragment_workout_logging_log_entry,
            container,
            false
        ) as LinearLayout
    }

    private fun highlightSet(rowIndex: Int?) {
        val rowToHighlight = rowIndex ?: workoutViewModel.getState().setIndex.value!!.minus(1)
        val isFirstSetInExercise = rowToHighlight >= 1

        if (isFirstSetInExercise) {
            setSetHighlightColor(rowToHighlight.minus(1), false)
            setHighlightBackground(rowToHighlight.minus(1), false)
        }

        setSetHighlightColor(rowToHighlight, true)
        setHighlightBackground(rowToHighlight, true)
    }

    private fun setSetHighlightColor(rowIndex: Int, isActive: Boolean) {
        val highlightColor = if (isActive) R.color.colorAccent else R.color.colorBlack
        val setsContainer = view!!.sets_entry_container

        setsContainer.getChildAt(rowIndex).txt_sets_count.setTextColor(
            ContextCompat.getColor(context!!, highlightColor)
        )
    }

    private fun setHighlightBackground(rowIndex: Int, isActive: Boolean) {
        var bgDrawable: Drawable? = null
        if (isActive) {
            bgDrawable = ContextCompat.getDrawable(context!!, R.drawable.tracking_log_active_background)
        }

        val setsContainer = view!!.sets_entry_container
        setsContainer.getChildAt(rowIndex).background = bgDrawable
    }
}
