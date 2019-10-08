package com.example.fitnessfatality.ui.screens.workoutDetails.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import kotlinx.android.synthetic.main.recycler_view_workout_details_exercise_list.view.*

class WorkoutExercisesListAdapter(
    private val workoutExerciseClickListener: OnWorkoutExerciseClickListener,
    var isRecyclerViewInEditMode: Boolean
) : RecyclerView.Adapter<WorkoutExercisesListAdapter.ViewHolder>() {
    private var dataSet: ArrayList<WorkoutExercisePojo> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemContainer = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.recycler_view_workout_details_exercise_list,
                parent,
                false
            ) as ConstraintLayout

        itemContainer.btn_info.setOnClickListener {
            val workoutExerciseData: WorkoutExercisePojo = itemContainer.tag as WorkoutExercisePojo
            this.workoutExerciseClickListener.onWorkoutExerciseInfoClick(workoutExerciseData)
        }

        return ViewHolder(itemContainer)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = dataSet[position]
        val noOfSets = itemData.workoutExercise!!.loggingTarget["sets"]
        val noOfReps = itemData.workoutExercise!!.loggingTarget["reps"]
        val restTimer = itemData.workoutExercise!!.loggingTarget["rest"]
        val loggingParams = "$noOfSets sets x $noOfReps reps $restTimer" + "s rest"

        holder.itemView.apply {
            tag = itemData
            lbl_exercise_name.text = itemData.exercise!!.name
            lbl_exercise_logging_params.text = loggingParams

            btn_info.visibility =
                if (isRecyclerViewInEditMode) View.GONE else View.VISIBLE

            btn_delete.visibility =
                if (isRecyclerViewInEditMode) View.VISIBLE else View.GONE
            
            btn_info.setOnClickListener {
                workoutExerciseClickListener.onWorkoutExerciseInfoClick(itemData)
            }

            btn_delete.setOnClickListener {
                Log.d("-->", "Position " + position)
                Log.d("-->", "ItemDataCount " + dataSet.count())
                dataSet.remove(itemData)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, 1)
                workoutExerciseClickListener.onWorkoutExerciseDeleteClick(itemData.workoutExercise!!)
            }
        }
    }

    fun updateDataSet(dataSet: ArrayList<WorkoutExercisePojo>) {
        if (!isRecyclerViewInEditMode) {
            this.dataSet = dataSet
            notifyDataSetChanged()
        }
    }

    class ViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem)
}