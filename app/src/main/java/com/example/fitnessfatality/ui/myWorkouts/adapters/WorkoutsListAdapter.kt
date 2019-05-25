package com.example.fitnessfatality.ui.myWorkouts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.workout.Workout
import kotlinx.android.synthetic.main.recycler_view_workout_list.view.*

class WorkoutsListAdapter(
    private val workoutsListListener: OnWorkoutListItemClickListener
): RecyclerView.Adapter<WorkoutsListAdapter.ViewHolder>(){
    var dataSet: List<Workout> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemContainer = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_workout_list, parent, false) as ConstraintLayout

        itemContainer.setOnClickListener {
            this.workoutsListListener.onWorkoutSelected(it)
        }
        return ViewHolder(itemContainer)
    }

    fun updateDataSet(dataSet: List<Workout>) {
        this.dataSet = dataSet
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = dataSet[position]
        holder.listItemView.tag = itemData
        holder.listItemView.lb_title.text = dataSet[position].name
    }

    class ViewHolder(val listItemView: View): RecyclerView.ViewHolder(listItemView)
}
