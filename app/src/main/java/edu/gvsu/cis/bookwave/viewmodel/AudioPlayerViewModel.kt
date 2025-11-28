package edu.gvsu.cis.bookwave.viewmodel

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.gvsu.cis.bookwave.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AudioPlayerViewModel(application: Application) : AndroidViewModel(application) {

    private var mediaPlayer: MediaPlayer? = null
    private var progressUpdateJob: Job? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _playbackSpeed = MutableStateFlow(1.0f)
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()

    private val availableSpeeds = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f)
    private var currentSpeedIndex = 2 // 1.0x par défaut

    fun initializePlayer(audioUrl: String) {
        releasePlayer()

        try {
            // Mapper chaque audioUrl vers le fichier correspondant dans res/raw
            val audioResource = when(audioUrl) {
                "art_of_war.mp3" -> R.raw.art_of_war
                "awakening.mp3" -> R.raw.awakening
                "ivanhoe.mp3" -> R.raw.ivanhoe
                "twentyyearsafter.mp3" -> R.raw.ivanhoe
                "wutheringheights.mp3" -> R.raw.wutheringheights
                "oliver_twist.mp3" -> R.raw.wutheringheights


                else -> {
                    // Fichier par défaut si aucun match
                    android.util.Log.w("AudioPlayer", "Audio non trouvé: $audioUrl, utilisation du premier disponible")
                    R.raw.audio_pride
                }
            }

            mediaPlayer = MediaPlayer.create(getApplication(), audioResource)

            mediaPlayer?.let { player ->
                _duration.value = player.duration.toLong()

                player.setOnCompletionListener {
                    _isPlaying.value = false
                    _currentPosition.value = 0
                    stopProgressUpdate()
                }
            }

            // Si le fichier n'existe pas, MediaPlayer.create retourne null
            if (mediaPlayer == null) {
                android.util.Log.e("AudioPlayer", "Erreur: Impossible de charger $audioUrl")
                _duration.value = 180000L // 3 minutes de durée simulée
            }

        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e("AudioPlayer", "Exception lors du chargement: ${e.message}")
            _duration.value = 180000L
        }
    }

    fun play() {
        mediaPlayer?.let { player ->
            try {
                if (!player.isPlaying) {
                    player.start()
                    _isPlaying.value = true
                    startProgressUpdate()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun pause() {
        mediaPlayer?.let { player ->
            try {
                if (player.isPlaying) {
                    player.pause()
                    _isPlaying.value = false
                    stopProgressUpdate()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun seekTo(position: Long) {
        mediaPlayer?.let { player ->
            try {
                player.seekTo(position.toInt())
                _currentPosition.value = position
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun skipForward(milliseconds: Long = 10000) {
        mediaPlayer?.let { player ->
            try {
                val newPosition = (player.currentPosition + milliseconds)
                    .coerceAtMost(player.duration.toLong())
                seekTo(newPosition)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun skipBackward(milliseconds: Long = 10000) {
        mediaPlayer?.let { player ->
            try {
                val newPosition = (player.currentPosition - milliseconds)
                    .coerceAtLeast(0)
                seekTo(newPosition)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun cyclePlaybackSpeed() {
        currentSpeedIndex = (currentSpeedIndex + 1) % availableSpeeds.size
        val newSpeed = availableSpeeds[currentSpeedIndex]
        _playbackSpeed.value = newSpeed

        mediaPlayer?.let { player ->
            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    player.playbackParams = player.playbackParams.setSpeed(newSpeed)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun startProgressUpdate() {
        stopProgressUpdate()
        progressUpdateJob = viewModelScope.launch {
            while (true) {
                mediaPlayer?.let { player ->
                    try {
                        if (player.isPlaying) {
                            _currentPosition.value = player.currentPosition.toLong()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                delay(100)
            }
        }
    }

    private fun stopProgressUpdate() {
        progressUpdateJob?.cancel()
        progressUpdateJob = null
    }

    fun releasePlayer() {
        stopProgressUpdate()
        try {
            mediaPlayer?.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mediaPlayer = null
        _isPlaying.value = false
        _currentPosition.value = 0
        _duration.value = 0
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }
}