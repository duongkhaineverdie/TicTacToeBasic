package com.moderngame.tictactoebasic.domain.interactors

import com.moderngame.tictactoebasic.data.repository.IRepository
import com.moderngame.tictactoebasic.domain.interactors.type.TrueColorBaseUseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetStatusPlaySoundUseCaseMatchGame(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : TrueColorBaseUseCaseFlow<Unit, Boolean>(dispatcher) {
    override suspend fun build(param: Unit): Flow<Boolean> = repository.getIsPlayingSound()

}