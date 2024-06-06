package com.moderngame.tictactoebasic.domain.repository

import com.moderngame.tictactoebasic.data.datastore.DataStore
import com.moderngame.tictactoebasic.data.repository.IRepository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val dataStore: DataStore,
) : IRepository {
    override fun getGameWon(): Flow<Int> = dataStore.gameWon
    override suspend fun saveGameWon(score: Int) = dataStore.storeGameWon(score)
    override fun getAvatarId(): Flow<Int> = dataStore.avatarId

    override suspend fun setAvatarId(id: Int) = dataStore.setAvatarId(id)
    override fun getIsPlayingSound(): Flow<Boolean> = dataStore.isPlayingSound

    override suspend fun setIsPlayingSound(isPlayingSound: Boolean) = dataStore.setIsPlayingSound(isPlayingSound)
}