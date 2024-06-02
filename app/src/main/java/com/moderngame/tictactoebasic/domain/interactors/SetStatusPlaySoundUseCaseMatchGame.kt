package com.moderngame.tictactoebasic.domain.interactors

import com.moderngame.tictactoebasic.data.repository.IRepository
import com.moderngame.tictactoebasic.domain.interactors.type.TrueColorBaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SetStatusPlaySoundUseCaseMatchGame(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : TrueColorBaseUseCase<Boolean, Unit>(dispatcher) {
    override suspend fun block(param: Boolean): Unit = repository.setIsPlayingSound(isPlayingSound = param)
}