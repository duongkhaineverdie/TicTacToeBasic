package com.moderngame.tictactoebasic.data.repository

import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getHighScore(): Flow<Int>
    suspend fun saveHighScore(score: Int)
    fun getAvatarId(): Flow<Int>
    suspend fun setAvatarId(id: Int)

    fun getIsPlayingSound(): Flow<Boolean>
    suspend fun setIsPlayingSound(isPlayingSound: Boolean)
}