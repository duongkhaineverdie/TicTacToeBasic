package com.moderngame.tictactoebasic.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ticTacToeGame")

class DataStore(private val context: Context) {
    init {
        context
    }

    companion object {
        val GAME_WON = intPreferencesKey("GAME_WON")
        val AVT_ID = intPreferencesKey("AVT_ID")
        val IS_PLAYING_SOUND = booleanPreferencesKey("IS_PLAYING_SOUND")
    }

    suspend fun storeGameWon(score: Int) {
        context.dataStore.edit {
            it[GAME_WON] = score
        }
    }

    suspend fun setAvatarId(id: Int) {
        context.dataStore.edit {
            it[AVT_ID] = id
        }
    }

    suspend fun setIsPlayingSound(isPlaying: Boolean) {
        context.dataStore.edit {
            it[IS_PLAYING_SOUND] = isPlaying
        }
    }

    val gameWon: Flow<Int> = context.dataStore.data.map {
        it[GAME_WON] ?: 0
    }

    val avatarId: Flow<Int> = context.dataStore.data.map {
        it[AVT_ID] ?: 0
    }
    val isPlayingSound: Flow<Boolean> = context.dataStore.data.map {
        it[IS_PLAYING_SOUND] ?: true
    }
}