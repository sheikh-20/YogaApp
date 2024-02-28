package com.bitvolper.yogazzz.presentation.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlayerStreamUIState(
    val isPlaying: Boolean = true,
    val currentTime: Long = 0L,
    val totalDuration: Long = 0L,
    val bufferedPercentage: Int = 0
)

@HiltViewModel
class MeditationViewModel @Inject constructor(val player: Player): ViewModel() {

    private var _playerStreamUIState = MutableStateFlow(PlayerStreamUIState())
    val playerStreamUIState: StateFlow<PlayerStreamUIState> get() = _playerStreamUIState.asStateFlow()

    private val handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable


    fun playVideoStream(file: String, context: Context) = viewModelScope.launch {

        _playerStreamUIState.update {
            it.copy( isPlaying = true)
        }

        player.setMediaItem(MediaItem.fromUri(file))
        player.play()

        runnable = object : Runnable {
            override fun run() {

                _playerStreamUIState.update {
                    it.copy(
                        totalDuration = player.contentDuration.coerceAtLeast(0L),
                        currentTime = player.currentPosition.coerceAtLeast(0L),
                        bufferedPercentage = player.bufferedPercentage
                    )
                }
                handler.postDelayed(this,500L)
            }
        }
        runnable.run()
    }

    fun playOrPauseVideo() {
        if (player.isPlaying) {
            player.pause()
            _playerStreamUIState.update {
                it.copy(isPlaying = false)
            }
        } else {
            player.play()
            _playerStreamUIState.update {
                it.copy(isPlaying = true)
            }
        }
    }

    fun onSeekTo(seekValue: Float) {
        player.seekTo(seekValue.toLong())
    }

    fun onMeditationStop() {
        player.pause()

        runnable = Runnable {  }
        handler.removeCallbacks(runnable)
    }

    override fun onCleared() {
        super.onCleared()
        player.release()

        runnable = Runnable {  }
        handler.removeCallbacks(runnable)
    }

    init {
        player.prepare()
    }
}