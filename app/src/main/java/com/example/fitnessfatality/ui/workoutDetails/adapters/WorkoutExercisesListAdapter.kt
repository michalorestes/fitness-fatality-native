package com.example.fitnessfatality.ui.workoutDetails.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import kotlinx.android.synthetic.main.recycler_view_workout_exercise_list.view.*

class WorkoutExercisesListAdapter: RecyclerView.Adapter<WorkoutExercisesListAdapter.ViewHolder>(){
    private var dataSet: List<WorkoutExercisePojo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemContainer = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_workout_exercise_list, parent, false) as ConstraintLayout

        return ViewHolder(itemContainer)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = dataSet[position]
        holder.itemView.tag = itemData
        holder.itemView.lbl_exercise_name.text = itemData.exercise!!.name
    }

    fun updateDataSet(dataSet: List<WorkoutExercisePojo>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class ViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem)
}