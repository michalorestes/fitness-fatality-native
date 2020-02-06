package com.example.fitnessfatality.ui.screens.workoutTracking.viewModels

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.fitnessfatality.data.database.AppDatabase
import com.example.fitnessfatality.data.models.pojo.WorkoutExercisePojo
import com.example.fitnessfatality.data.models.workoutSession.SessionLog
import com.example.fitnessfatality.data.models.workoutSession.WorkoutSession
import com.example.fitnessfatality.data.repository.SessionLogRepository
import com.example.fitnessfatality.data.repository.WorkoutExerciseRepository
import com.example.fitnessfatality.data.repository.WorkoutSessionRepository
import com.example.fitnessfatality.ui.base.BaseViewModel
import com.example.fitnessfatality.ui.screens.workoutTracking.interfaces.ViewPagerPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class TrackingViewModel(application: Application) : BaseViewModel(application) {
    interface FragmentProvider {
        fun getCurrentPage(): ViewPagerPage
        fun displayNextPage()
        fun setAdapterData(workoutExercises: List<WorkoutExercisePojo>)
        fun updateBottomSheetUi(workoutExercises: List<WorkoutExercisePojo>)
    }

    var currentRestTime = MutableLiveData(0)
    val controlsEnabled = MutableLiveData(true)

    private var currentExercise: MutableLiveData<WorkoutExercisePojo> = MutableLiveData()
    private var currentSet = MutableLiveData(1)
    private var exerciseIndex = 0
    private val isEndOfSession = MutableLiveData<Boolean>(false)

    private var workoutExercises: LiveData<List<WorkoutExercisePojo>>
    private val workoutExerciseRepository: WorkoutExerciseRepository
    public val sessionLogRepository: SessionLogRepository //TODO: This can't stay public
    private val workoutSessionRepository: WorkoutSessionRepository

    private var fragmentInteractions: FragmentProvider? = null

    private var session: WorkoutSession? = null
    private val sessionLogs: HashMap<Int, SessionLog> = hashMapOf()

    init {
        val db = AppDatabase.getDatabase(application, scope)
        workoutExerciseRepository = WorkoutExerciseRepository(db.workoutExerciseDao())
        sessionLogRepository = SessionLogRepository(db.sessionLogDao())
        workoutSessionRepository = WorkoutSessionRepository(db.workoutSessionDao())
        workoutExercises = MutableLiveData()
        //todo: Disable controller by default
    }

    suspend fun initialiseWorkoutSession(workoutId: Int) {
        session = WorkoutSession(
            workoutId = workoutId,
            startDate = LocalDateTime.now()
        )

        session!!.id = workoutSessionRepository.insert(session!!)

        GlobalScope.launch(Dispatchers.Main) {
            workoutExercises = withContext(Dispatchers.Default) {
                MutableLiveData(
                    workoutExerciseRepository.findWorkoutExercisesByWorkoutIdBlocking(workoutId)
                )
            }

            if (workoutExercises.value!!.isNotEmpty()) {
                currentExercise.value = workoutExercises.value!![exerciseIndex]
                fragmentInteractions!!.setAdapterData(workoutExercises.value!!)
                fragmentInteractions!!.updateBottomSheetUi(workoutExercises.value!!)
                //TODO: Enable controls once data is loaded
            }
        }
    }

    fun onNextClick(view: View) {
        storeExerciseLogs()
        if (!isEndOfSession()) {
            incrementIndexesToNextLog()
            startRestTimer()
        } else {
            endSession()
        }
    }

    private fun storeExerciseLogs() {
        val workoutExercise = workoutExercises.value!![exerciseIndex]
        var sessionLog: SessionLog? = sessionLogs[workoutExercise.workoutExercise!!.id]
        if (sessionLogs[workoutExercise.workoutExercise!!.id] == null) {
            sessionLog = SessionLog(
                sessionId = session!!.id!!,
                exerciseId = workoutExercise.exercise!!.id,
                workoutId = session!!.workoutId,
                workoutExerciseId = workoutExercise.workoutExercise!!.id!!,
                type = workoutExercise.workoutExercise!!.selectedLoggingType,
                value = hashMapOf()
            )

            GlobalScope.launch {
                sessionLog.id = sessionLogRepository.insert(sessionLog)
            }

        }

//        sessionLog!!.value[currentSet.value!!] =  hashMapOf(
//            LocalDateTime.now() to fragmentInteractions!!.getCurrentPage().getCurrentSetLog()
//        )

        sessionLog!!.value[currentSet.value!!] =  hashMapOf(
            LocalDateTime.now() to hashMapOf("1" to "2.1")
        )

        GlobalScope.launch {
            sessionLogRepository.update(sessionLog)
        }
    }

    private fun isEndOfSession(): Boolean {
        val currentExerciseNumberOfSets = currentExercise
            .value!!
            .workoutExercise!!
            .loggingTarget["sets"]!!
            .toInt()

        val isLastExercise = (workoutExercises.value!!.size - 1) == exerciseIndex
        val isLastSet = (currentExerciseNumberOfSets) == currentSet.value!!

        return isLastExercise && isLastSet
    }

    private fun incrementIndexesToNextLog() {
        if (isNextSetAvailable()) {
            incrementCurrentSetIndex()
        } else if (isNextExerciseAvailable()) {
            proceedToNextExercise()
        }
    }

    private fun isNextSetAvailable(): Boolean {
        val setsTarget = currentExercise
            .value!!
            .workoutExercise!!
            .loggingTarget["sets"]!!
            .toInt()

        return currentSet.value!! < setsTarget
    }

    private fun incrementCurrentSetIndex() {
        currentSet.value = currentSet.value!!.plus(1)
    }

    private fun isNextExerciseAvailable(): Boolean {
        return exerciseIndex < (workoutExercises.value!!.size.minus(1))
    }

    private fun proceedToNextExercise() {
        exerciseIndex++
        currentExercise.value = workoutExercises.value!![exerciseIndex]
        currentSet.value = 1
        fragmentInteractions!!.displayNextPage()
    }

    private fun startRestTimer() {
        enableControls(false)
//        TODO: Disabled real timer for debugging
//        val restTimeSeconds = currentExercise
//            .value!!
//            .workoutExercise!!
//            .loggingTarget["rest"]!!
//            .toInt()

        val restTimeSeconds = 1
        currentRestTime.value = restTimeSeconds

        val timer = object : CountDownTimer(
            (restTimeSeconds * 1000).toLong(),
            1000
        ) {
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

    fun setViewPagerProvider(viewPagerProvider: FragmentProvider) {
        this.fragmentInteractions = viewPagerProvider
    }

    fun getCurrentSet(): MutableLiveData<Int> {
        return currentSet
    }

    fun getCurrentExerciseIndex(): Int {
        return exerciseIndex
    }
}
