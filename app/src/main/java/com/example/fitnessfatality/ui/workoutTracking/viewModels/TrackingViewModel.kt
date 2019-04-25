package com.example.fitnessfatality.ui.workoutTracking.viewModels

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.logging.ExerciseLog
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.repository.ExerciseLogRepository
import com.example.fitnessfatality.data.repository.WorkoutExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TrackingViewModel(application: Application) : BaseViewModel(application) {
    val uiData = MutableLiveData<UI>()
    val workoutExercises: LiveData<List<WorkoutExercisePojo>>

    var currentExerciseIndex: MutableLiveData<Int> = MutableLiveData()
    var currentSetIndex = MutableLiveData<Int>()

    val currentLog: Map<String, String>

    private val workoutExerciseRepository: WorkoutExerciseRepository
    private val exerciseLogRepository: ExerciseLogRepository

    init {
        val db = AppDatabase.getDatabase(application, scope)
        workoutExerciseRepository = WorkoutExerciseRepository(db.workoutExerciseDao())
        workoutExercises = workoutExerciseRepository.allWorkoutExercises
        exerciseLogRepository = ExerciseLogRepository(db.exerciseLogDao())

        currentExerciseIndex.value = 0
        currentSetIndex.value = STARTING_SET
        uiData.value = UI()
        currentLog = mapOf("repsNo" to "0", "liftedWeight" to "0.0")
    }

    fun initialiseUi() {
        val data: WorkoutExercisePojo = workoutExercises.value!![currentExerciseIndex.value!!]
        uiData.value = UI(
            exerciseName = data.exercise!!.name,
            setsInformation = "${currentSetIndex.value!!} / ${data.workoutExercise!!.loggingTarget["sets"]}",
            restTimer = data.workoutExercise!!.loggingTarget["rest"]!!
        )
    }

    fun onNextHandler() {
        val isLastExercise: Boolean = workoutExercises.value!!.size <= currentExerciseIndex.value!!
        if (isLastExercise) {
            enableControls(false)
            return
        }

        saveLogData()
        val data: WorkoutExercisePojo = workoutExercises.value!![currentExerciseIndex.value!!]
        incrementIndexesToNextLog(data)
        updateUiData(data)
        startRestTimer()
    }

    private fun enableControls(isEnabled: Boolean) {
        val ui = uiData.value!!
        ui.isNextEnabled = isEnabled
        uiData.value = ui
    }

    private fun saveLogData() {
        val workoutExercisePojo: WorkoutExercisePojo = workoutExercises.value!![currentExerciseIndex.value!!]
        val log = ExerciseLog(
            date = LocalDateTime.now(),
            exerciseId = workoutExercisePojo.exercise!!.id,
            workoutExerciseId = workoutExercisePojo.workoutExercise!!.id,
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

    private fun incrementIndexesToNextLog(data: WorkoutExercisePojo) {
        val isLastSet: Boolean = data.workoutExercise!!.loggingTarget["sets"]!! <= currentSetIndex.value!!
        if (isLastSet) {
            currentSetIndex.value = STARTING_SET
        } else {
            currentSetIndex.value = currentSetIndex.value!!.plus(1)
        }

        currentExerciseIndex.value = currentExerciseIndex.value!!.plus(1)
    }

    private fun updateUiData(data: WorkoutExercisePojo) {
        uiData.value = UI(
            exerciseName = data.exercise!!.name,
            setsInformation = "${currentSetIndex.value!!} / ${data.workoutExercise!!.loggingTarget["sets"]}",
            restTimer = data.workoutExercise!!.loggingTarget["rest"]!!
        )
    }

    private fun startRestTimer() {
        enableControls(false)
        val timer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val data = uiData.value!!
                data.restTimer--
                uiData.value = data
            }

            override fun onFinish() {
                enableControls(true)
            }
        }
        timer.start()
    }

    fun getLogs(): LiveData<List<ExerciseLog>> {
        return exerciseLogRepository.allLogs
    }

    class UI(
        var exerciseName: String = "",
        var setsInformation: String = "",
        var isNextEnabled: Boolean = true,
        var restTimer: Int = 0
    )

    companion object {
        const val STARTING_SET: Int = 1
    }
}
