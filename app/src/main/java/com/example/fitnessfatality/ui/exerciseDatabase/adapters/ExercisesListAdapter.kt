package com.example.fitnessfatality.ui.exerciseDatabase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.exercise.Exercise
import kotlinx.android.synthetic.main.recycler_view_exercises_list.view.*

class ExercisesListAdapter: RecyclerView.Adapter<ExercisesListAdapter.ViewHolder>() {

    private var dataSet: List<Exercise> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemContainer = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_exercises_list, parent, false) as  LinearLayout

        return ViewHolder(itemContainer)
    }

    fun updateDataSet(dataSet: List<Exercise>) {
        this.dataSet = dataSet
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = dataSet[position]
        holder.itemView.lbl_exercise_name.text = itemData.name
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}
