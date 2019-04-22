package com.example.fitnessfatality.ui.workoutTracking.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.repository.WorkoutExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel

class TrackingViewModel(application: Application) : BaseViewModel(application) {
    val uiData = MutableLiveData<UI>()
    val workoutExercises: LiveData<List<WorkoutExercisePojo>>
    var currentExerciseIndex: MutableLiveData<Int> = MutableLiveData()
    var currentSetIndex = MutableLiveData<Int>()
    private val workoutExerciseRepository: WorkoutExerciseRepository

    init {
        val db = AppDatabase.getDatabase(application, scope)
        workoutExerciseRepository = WorkoutExerciseRepository(db.workoutExerciseDao())
        workoutExercises = workoutExerciseRepository.allWorkoutExercises
        currentExerciseIndex.value = 0
        currentSetIndex.value = STARTING_SET
        uiData.value = UI()
    }

    fun onNextHandler() {
        //TODO: Save user input data first logic should go on this line
        val isLastExercise: Boolean = workoutExercises.value!!.size - 1 <= currentExerciseIndex.value!!
        if (isLastExercise) {
            //TODO: Display end screen when true
            Log.d("Tag-->", "End of workout logging")
            return
        }

        val data: WorkoutExercisePojo = workoutExercises.value!![currentExerciseIndex.value!!]
        incrementIndexesToNextLog(data)
        updateUiData(data)
    }

    private fun incrementIndexesToNextLog(data: WorkoutExercisePojo) {
        val isLastSet: Boolean = data.workoutExercise!!.loggingTarget["sets"]!! < currentSetIndex.value!!
        if (!isLastSet) {
            currentSetIndex.value = currentSetIndex.value!! + 1
        } else {
            currentSetIndex.value = STARTING_SET
        }
        currentExerciseIndex.value = currentExerciseIndex.value!! + 1
    }

    private fun updateUiData(data: WorkoutExercisePojo) {
        uiData.value = UI(
            exerciseName = data.exercise!!.name,
            setsInformation = "${currentSetIndex.value!!} / ${data.workoutExercise!!.loggingTarget["sets"]}"
        )
    }

    fun initialise() {
        val data: WorkoutExercisePojo = workoutExercises.value!![currentExerciseIndex.value!!]
        uiData.value = UI(
            exerciseName = data.exercise!!.name,
            setsInformation = "${currentSetIndex.value!!} / ${data.workoutExercise!!.loggingTarget["sets"]}"
        )
    }

    class UI(
        val exerciseName: String = "",
        val setsInformation: String = ""
    )

    companion object {
        const val STARTING_SET: Int = 1
    }
}
