package com.example.fitnessfatality.ui.screens.workoutTracking.viewModels

import android.app.Application
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.repository.ExerciseLogRepository
import com.example.fitnessfatality.data.repository.WorkoutExerciseRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackingViewModel(application: Application) : BaseViewModel(application) {
    var currentExercise: MutableLiveData<WorkoutExercisePojo> = MutableLiveData()
    var currentSet = MutableLiveData(0)
    var currentRestTime = MutableLiveData(0)
    val controlsEnabled = MutableLiveData(true)
    val isEndOfSession = MutableLiveData<Boolean>(false)

    private var workoutExercises: LiveData<List<WorkoutExercisePojo>>
    private val workoutExerciseRepository: WorkoutExerciseRepository
    private val exerciseLogRepository: ExerciseLogRepository
    private var exerciseIndex = 0

    init {
        val db = AppDatabase.getDatabase(application, scope)
        workoutExerciseRepository = WorkoutExerciseRepository(db.workoutExerciseDao())
        exerciseLogRepository = ExerciseLogRepository(db.exerciseLogDao())
        workoutExercises = MutableLiveData()
    }

    suspend fun loadWorkoutExercises(workoutId: Int) = GlobalScope.launch(Dispatchers.Main) {
        workoutExercises = withContext(Dispatchers.Default) {
            MutableLiveData(
                workoutExerciseRepository.findWorkoutExercisesByWorkoutIdBlocking(workoutId)
            )
        }

        if (workoutExercises.value!!.isNotEmpty()) {
            currentExercise.value = workoutExercises.value!![exerciseIndex]
        }
    }

    fun onNextClick(view: View) {

        if (!isEndOfSession()) {
            incrementIndexesToNextLog()
            startRestTimer()
        } else {
            endSession()
        }
    }

    private fun isEndOfSession(): Boolean {
        val currentExerciseNumberOfSets = currentExercise
            .value!!
            .workoutExercise!!
            .loggingTarget["sets"]!!
            .toInt()

        val isLastExercise = (workoutExercises.value!!.size - 1) == exerciseIndex
        val isLastSet = (currentExerciseNumberOfSets - 1) == currentSet.value!!

        return isLastExercise && isLastSet
    }

    private fun incrementIndexesToNextLog() {
        val currentExerciseNumberOfSets = currentExercise
            .value!!
            .workoutExercise!!
            .loggingTarget["sets"]!!
            .toInt()
        if(currentSet.value!! < (currentExerciseNumberOfSets.minus(1)))  {
            currentSet.value = currentSet.value!!.plus(1)
        } else if (exerciseIndex < (workoutExercises.value!!.size.minus(1))) {
            currentSet.value = 0
            exerciseIndex++
            currentExercise.value = workoutExercises.value!![exerciseIndex]
        }
    }

    private fun startRestTimer() {
        enableControls(false)
//        TODO: Disabled real timer for debugging
//        val restTimeSeconds = currentExercise
//            .value!!
//            .workoutExercise!!
//            .loggingTarget["rest"]!!
//            .toInt()

        val restTimeSeconds = 2
        currentRestTime.value = restTimeSeconds

        val timer = object: CountDownTimer((restTimeSeconds * 1000).toLong(), 1000) {
            override fun onFinish() {
                enableControls(true)
            }

            override fun onTick(p0: Long) {
                currentRestTime.value = currentRestTime.value!!.minus(1)
            }
        }

        timer.start()
    }

    private fun enableControls(isEnabled: Boolean) {
        //TODO: Disable & enable remaining controls
        controlsEnabled.value = isEnabled
    }

    private fun endSession() {
        isEndOfSession.value = true
        enableControls(false)
    }
}
