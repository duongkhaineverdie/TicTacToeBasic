package com.moderngame.tictactoebasic.domain.interactors

import com.moderngame.tictactoebasic.data.repository.IRepository
import com.moderngame.tictactoebasic.domain.interactors.type.TrueColorBaseUseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetHighScoreUseCaseMatchGame(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : TrueColorBaseUseCaseFlow<Unit, Int>(dispatcher) {
    override suspend fun build(param: Unit): Flow<Int> = repository.getHighScore()

}