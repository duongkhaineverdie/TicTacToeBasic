package com.moderngame.tictactoebasic.data.soundmanager

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.moderngame.tictactoebasic.R


class SoundManager(context: Context) {
    private val attributes: AudioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .setUsage(AudioAttributes.USAGE_GAME)
        .build()

    private val soundPool: SoundPool = SoundPool.Builder()
        .setAudioAttributes(attributes)
        .setMaxStreams(1) // Allow only one sound to play at a time
        .build()

    private val soundId = soundPool.load(context, R.raw.game_click, 1)
    fun playSound() {
        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }
}