package com.example.fitnessfatality.ui.workoutDetails.exerciseDatabase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.exercise.Exercise
import kotlinx.android.synthetic.main.recycler_view_exercises_list.view.*

class ExercisesListAdapter(
    private val onExerciseListListener: OnExerciseListListener
): RecyclerView.Adapter<ExercisesListAdapter.ViewHolder>() {

    private var dataSet: List<Exercise> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemContainer = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_exercises_list, parent, false) as  ConstraintLayout

        val addToWorkoutBtn = itemContainer.findViewById<ImageButton>(R.id.btn_add_to_workout)

        addToWorkoutBtn.setOnClickListener {
            val exercise: Exercise = itemContainer.tag as Exercise
            this.onExerciseListListener.onAddExerciseToWorkout(exercise)
        }

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
        holder.itemView.tag = itemData
        holder.itemView.lbl_exercise_name.text = itemData.name
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}
