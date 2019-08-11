package com.example.fitnessfatality.ui.screens.workoutTracking.viewModels

import android.app.Application
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.logging.ExerciseLog
import com.example.fitnessfatality.data.models.logging.LoggingType
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.models.workout.WorkoutExercise
import com.example.fitnessfatality.data.repository.ExerciseLogRepository
import com.example.fitnessfatality.data.repository.WorkoutExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TrackingViewModel(application: Application) : BaseViewModel(application) {

    var workoutExercises: LiveData<List<WorkoutExercisePojo>>

    val isNextEnabled = MutableLiveData<Boolean>()
    val currentExerciseName = MutableLiveData<String>()
    val restTimer = MutableLiveData<Int>()
    val currentLog: Map<String, String>
    val currentExerciseTotalSetsNo = MutableLiveData<Int>()
    val currentDuration = MutableLiveData<Int>()
    val currentLoggingType = MutableLiveData<LoggingType>()

    private val currentExerciseIndex: MutableLiveData<Int> = MutableLiveData()
    val currentSetIndex = MutableLiveData<Int>()

    private val workoutExerciseRepository: WorkoutExerciseRepository
    private val exerciseLogRepository: ExerciseLogRepository

    init {
        val db = AppDatabase.getDatabase(application, scope)
        workoutExerciseRepository = WorkoutExerciseRepository(db.workoutExerciseDao())
        exerciseLogRepository = ExerciseLogRepository(db.exerciseLogDao())
        workoutExercises = MutableLiveData()
        currentExerciseIndex.value = 0
        currentSetIndex.value = STARTING_SET
        currentDuration.value = 0
        currentLog = mapOf("repsNo" to "0", "liftedWeight" to "0.0")
    }

    //TODO: This should be done with a ViewModel factory but let's leave it like this for now!!!!!
    fun loadWorkoutExercises(workoutId: Int) {
        workoutExercises = workoutExerciseRepository.findWorkoutExercisesByWorkoutId(workoutId)
    }

    fun onNextHandler(view: View) {
        //TODO: This function should identify when there is one exercise left and update the "Next" btn to
        //TODO: display "COMPLETE WORKOUT"
        val isLastExercise: Boolean = (workoutExercises.value!!.size - 1) <= currentExerciseIndex.value!!
        if (isLastExercise) {
            enableControls(false)
            view.findNavController().navigate(R.id.action_workoutLoggingFragment_to_trackingEndFragment)
            return
        }

        saveLogData()
        incrementIndexesToNextLog()
        updateUiData()
        startRestTimer()
    }

    private fun saveLogData() {
        val workoutExercisePojo: WorkoutExercisePojo = workoutExercises.value!![currentExerciseIndex.value!!]
        val log = ExerciseLog(
            date = LocalDateTime.now(),
            exerciseId = workoutExercisePojo.exercise!!.id,
            workoutExerciseId = workoutExercisePojo.workoutExercise!!.id!!,
            workoutId = 0, //TODO: Replace with real workout ID once accessible
            type = workoutExercisePojo.workoutExercise!!.selectedLoggingType,
            value = mapOf(
                "setIndex" to currentSetIndex.value!!.toString(),
                "reps" to (currentLog["repsNo"] ?: error("0")),
                "liftedWeight" to (currentLog["liftedWeight"] ?: error("0.0"))
            )
        )

        scope.launch(Dispatchers.IO) {
            exerciseLogRepository.insert(log)
        }
    }

    private fun incrementIndexesToNextLog() {
        val data: WorkoutExercisePojo = workoutExercises.value!![currentExerciseIndex.value!!]
        val isLastSet: Boolean = data.workoutExercise!!.loggingTarget["sets"]!! <= currentSetIndex.value!!
        if (isLastSet) {
            currentSetIndex.value = STARTING_SET
            currentExerciseIndex.value = currentExerciseIndex.value!!.plus(1)
        } else {
            currentSetIndex.value = currentSetIndex.value!!.plus(1)
        }
    }

    fun updateUiData() {
        val data: WorkoutExercisePojo = workoutExercises.value!![currentExerciseIndex.value!!]
        currentExerciseName.value = data.exercise!!.name
        updateLoggingType(data.workoutExercise!!)
        enableControls(true)
    }

    private fun updateLoggingType(workoutExercise: WorkoutExercise) {
        currentLoggingType.value = workoutExercise.selectedLoggingType
        if (workoutExercise.selectedLoggingType == LoggingType.WEIGHTS) {
            currentExerciseTotalSetsNo.value = workoutExercise.loggingTarget["sets"]
        } else if (workoutExercise.selectedLoggingType == LoggingType.DURATION) {
            currentDuration.value = workoutExercise.loggingTarget["duration"]
        }

        restTimer.value = workoutExercise.loggingTarget["rest"]!!
    }

    //TODO: Timer can be encapsulated in its own Compound view
    private fun startRestTimer() {
        enableControls(false)
        val timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restTimer.value = restTimer.value!! - 1
            }

            override fun onFinish() {
                enableControls(true)
            }
        }
        timer.start()
    }

    private fun enableControls(isEnabled: Boolean) {
        isNextEnabled.value = isEnabled
    }

    fun getLogs(): LiveData<List<ExerciseLog>> {
        return exerciseLogRepository.allLogs
    }

    fun getWorkoutWorkoutExercises(): LiveData<List<WorkoutExercisePojo>> {
        return workoutExercises
    }

    companion object {
        const val STARTING_SET: Int = 1
    }
}
