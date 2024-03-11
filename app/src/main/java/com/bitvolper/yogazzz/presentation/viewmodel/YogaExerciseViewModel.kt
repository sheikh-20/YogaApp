package com.bitvolper.yogazzz.presentation.viewmodel

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bitvolper.yogazzz.domain.model.SerenityData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

data class ExerciseUIState(val exercise: SerenityData.Data.Pose = SerenityData.Data.Pose(),
                           val timer: Int = 10,
                           val showExerciseDetails: Boolean = false,
                           val exerciseTimer: Int = 30)
class YogaExerciseViewModel: ViewModel() {

    companion object {
        private const val TAG = "YogaExerciseViewModel"
        const val TIME_LEFT_MILLI_SEC = 10_000L
        const val EXERCISE_TIME_LEFT_MILLI_SEC = 30_000L
    }

    private lateinit var timer: CountDownTimer
    private var timeInMilliSec: Long = TIME_LEFT_MILLI_SEC

    private var _currentExercise = MutableStateFlow(ExerciseUIState())
    val currentExercise: StateFlow<ExerciseUIState> get() = _currentExercise

    var currentExerciseIndex = mutableIntStateOf(0)
        private set

    fun startExercise(serenityData: List<SerenityData.Data.Pose?>, nextScreen: () -> Unit = {  }, completeScreen: () -> Unit = { }) {
        _currentExercise.update {
            it.copy(exercise = serenityData[currentExerciseIndex.intValue] ?: return)
        }

        timer = object : CountDownTimer(TIME_LEFT_MILLI_SEC, 1_000L) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMilliSec = millisUntilFinished

                _currentExercise.update {
                    it.copy(
                        timer = currentExercise.value.timer.dec()
                    )
                }
            }

            override fun onFinish() {
                _currentExercise.update {
                    it.copy(
                        timer = 0,
                        showExerciseDetails = true
                    )
                }

                exerciseTimer(serenityData, nextScreen, completeScreen)
            }
        }.start()
    }

    fun exerciseTimer(serenityData: List<SerenityData.Data.Pose?>, nextScreen: () -> Unit = {  }, completeScreen: () -> Unit = { }) {
        timer = object : CountDownTimer(EXERCISE_TIME_LEFT_MILLI_SEC, 1_000L) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMilliSec = millisUntilFinished

                _currentExercise.update {
                    it.copy(
                        exerciseTimer = currentExercise.value.exerciseTimer.dec()
                    )
                }
            }

            override fun onFinish() {
                _currentExercise.update {
                    it.copy(
                        exerciseTimer = 0
                    )
                }

                currentExerciseIndex.value++

                if (currentExerciseIndex.value >= serenityData.size - 1) {
                    Timber.tag(TAG).d("Complete -> Size: ${serenityData.size} Index: $currentExerciseIndex")
                    completeScreen()
                } else {
                    Timber.tag(TAG).d("Next screen -> Size: ${serenityData.size} Index: $currentExerciseIndex")
                    _currentExercise.update {
                        it.copy(
                            exerciseTimer = 30,
                            exercise = serenityData[currentExerciseIndex.value.inc()] ?: SerenityData.Data.Pose()
                            )
                    }
                    timer.cancel()
                    nextScreen()
                    exerciseTimer(serenityData, nextScreen, completeScreen)
                }
            }
        }.start()
    }
}