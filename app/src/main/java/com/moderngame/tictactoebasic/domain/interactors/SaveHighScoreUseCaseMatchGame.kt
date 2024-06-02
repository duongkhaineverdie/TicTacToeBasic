package com.moderngame.tictactoebasic.domain.interactors

import com.moderngame.tictactoebasic.data.repository.IRepository
import com.moderngame.tictactoebasic.domain.interactors.type.TrueColorBaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SaveHighScoreUseCaseMatchGame(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : TrueColorBaseUseCase<Int, Unit>(dispatcher) {
    override suspend fun block(param: Int): Unit = repository.saveHighScore(score = param)
}