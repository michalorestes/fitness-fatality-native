package com.example.fitnessfatality.ui.screens.homeScreen.myRoutines.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.routine.Routine
import kotlinx.android.synthetic.main.recycler_view_workout_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class RoutinesListAdapter(
    private val workoutsListListener: OnWorkoutListItemClickListener,
    private val resources: Resources,
    var isInEditMode: Boolean,
    private val fragment: ListGestureFragmentInterface
) : RecyclerView.Adapter<RoutinesListAdapter.ViewHolder>(), ListGesturesCallbackInterface {
    private var dataSet: ArrayList<Routine> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemContainer = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_workout_list,
                parent,
                false
            ) as FrameLayout


        itemContainer.routine_item_constraint_layout.setOnClickListener {
            val routine: Routine = itemContainer.tag as Routine
            workoutsListListener.onWorkoutSelected(it, routine)
        }

        itemContainer.routine_item_constraint_layout.chip_start_workout.setOnClickListener {
            val routine: Routine = itemContainer.tag as Routine
            workoutsListListener.onWorkoutSessionSelected(it, routine)
        }

        return ViewHolder(itemContainer)
    }

    fun updateDataSet(dataSet: ArrayList<Routine>) {
        this.dataSet = dataSet
        if (!isInEditMode) {
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = dataSet[position]
        holder.apply {
            listItemView.tag = itemData
            listItemView.lb_title.text = itemData.name
            listItemView
                .img_workout_icon
                .setImageDrawable(resources.getDrawable(itemData.workoutIcon))

            listItemView.chip_start_workout.visibility =
                if (isInEditMode) View.GONE else View.VISIBLE

            listItemView.btn_delete.visibility =
                if (isInEditMode) View.VISIBLE else View.GONE

            listItemView.btn_drag.visibility =
                if (isInEditMode) View.VISIBLE else View.GONE

            listItemView.img_workout_icon.visibility =
                if (isInEditMode) View.GONE else View.VISIBLE

            listItemView.btn_drag.setOnTouchListener { v, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    fragment.startDrag(holder)
                }

                return@setOnTouchListener true
            }

            listItemView.btn_delete.setOnClickListener {
                workoutsListListener.onRoutineDelete(it, itemData)
                dataSet.remove(itemData)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, 1)

            }
        }
    }

    class ViewHolder(val listItemView: View) : RecyclerView.ViewHolder(listItemView)

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(dataSet, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(dataSet, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }
}
