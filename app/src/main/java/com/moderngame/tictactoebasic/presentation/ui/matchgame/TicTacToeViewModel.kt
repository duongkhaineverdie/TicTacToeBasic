package com.moderngame.tictactoebasic.presentation.ui.matchgame

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moderngame.tictactoebasic.R
import com.moderngame.tictactoebasic.data.soundmanager.SoundManager
import com.moderngame.tictactoebasic.domain.interactors.GetAvtIdUseCase
import com.moderngame.tictactoebasic.domain.interactors.GetHighScoreUseCaseMatchGame
import com.moderngame.tictactoebasic.domain.interactors.GetStatusPlaySoundUseCaseMatchGame
import com.moderngame.tictactoebasic.domain.interactors.SaveHighScoreUseCaseMatchGame
import com.moderngame.tictactoebasic.utils.Constants
import com.moderngame.tictactoebasic.utils.GameLevels
import com.moderngame.tictactoebasic.utils.TicTacToe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TicTacToeViewModel(
    savedStateHandle: SavedStateHandle,
    private val getHighScoreUseCase: GetHighScoreUseCaseMatchGame,
    private val saveHighScoreUseCase: SaveHighScoreUseCaseMatchGame,
    private val getStatusPlaySoundUseCaseMatchGame: GetStatusPlaySoundUseCaseMatchGame,
    private val getAvtIdUseCase: GetAvtIdUseCase,
    private val soundManager: SoundManager,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<NumberGuessingGameState> =
        MutableStateFlow(NumberGuessingGameState())

    val stateFlow: StateFlow<NumberGuessingGameState> = _stateFlow.asStateFlow()

    init {
        _stateFlow.update {
            it.copy(
                levelsGame = try {
                    checkNotNull(savedStateHandle["levels"])
                } catch (e: NullPointerException) {
                    GameLevels.EASY.toString()
                }
            )
        }
        getAvatarId()
        getStatusPlayingSound()
        getHighScore()
        viewModelScope.launch {
            val timeDelay = 100L
            while (isActive) {
                if (stateFlow.value.timeAlive > 0L && stateFlow.value.isPlaying) {
                    val timeLeft = stateFlow.value.timeAlive - timeDelay
                    if (timeLeft > 0) {
                        _stateFlow.update {
                            it.copy(
                                timeAlive = stateFlow.value.timeAlive - timeDelay,
                            )
                        }
                    } else {
                        if (stateFlow.value.realPlayer != stateFlow.value.currentPlayer) {
                            if (stateFlow.value.levelsGame == GameLevels.EASY.name) {
                                turnComputerRandom()
                            } else {
                                turnComputerEvil()
                            }
                        } else {
                            _stateFlow.update {
                                it.copy(
                                    winner = returnAnotherTicTacToe(stateFlow.value.realPlayer),
                                    isPlaying = false,
                                )
                            }
                        }
                    }
                }
                delay(timeDelay)
            }
        }
    }

    private fun getAvatarId() {
        viewModelScope.launch {
            getAvtIdUseCase(Unit).collectLatest { result ->
                result.onSuccess { value ->
                    _stateFlow.update {
                        it.copy(
                            avatarPlayerId = value
                        )
                    }
                }
            }
        }
    }

    private fun turnComputerRandom() {
        val currentBoard = stateFlow.value.board
        val availableMoves = mutableListOf<Int>()

        // Find all empty cells on the board
        for (row in currentBoard.indices) {
            if (currentBoard[row] == TicTacToe.EMPTY) {
                availableMoves.add(row)
            }
        }

        // If there are available moves, choose one randomly and place the computer's symbol
        if (availableMoves.isNotEmpty()) {
            val randomIndex = (0 until availableMoves.size).random()
            val row = availableMoves[randomIndex]
            val computerSymbol = returnAnotherTicTacToe(stateFlow.value.realPlayer)
            currentBoard[row] = computerSymbol
            checkWinner(currentBoard)
        }
    }

    private fun turnComputerEvil() {
        val board = stateFlow.value.board
        var bestScore = Int.MIN_VALUE
        var move = -1
        for (i in board.indices) {
            if (board[i] == TicTacToe.EMPTY) {
                board[i] = stateFlow.value.currentPlayer
                val score = minimax(board, 0, false)
                board[i] = TicTacToe.EMPTY
                if (score > bestScore) {
                    bestScore = score
                    move = i
                }
            }
        }
        if (move != -1) {
            board[move] = stateFlow.value.currentPlayer
        }
        checkWinner(board)
    }

    private fun checkWin(board: MutableList<TicTacToe>, player: TicTacToe): Boolean {
        val winConditions = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
        )
        for (condition in winConditions) {
            if (board[condition[0]] == player && board[condition[1]] == player && board[condition[2]] == player  && player != TicTacToe.EMPTY) {
                return true
            }
        }
        return false
    }

    private fun isBoardFull(board: MutableList<TicTacToe>): Boolean {
        return board.all { it != TicTacToe.EMPTY }
    }
    private fun minimax(board: MutableList<TicTacToe>, depth: Int, isMaximizing: Boolean): Int {
        if (checkWin(board, TicTacToe.O)) return 1
        if (checkWin(board, TicTacToe.X)) return -1
        if (isBoardFull(board)) return 0

        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (i in board.indices) {
                if (board[i] == TicTacToe.EMPTY) {
                    board[i] = TicTacToe.O
                    val score = minimax(board, depth + 1, false)
                    board[i] = TicTacToe.EMPTY
                    bestScore = maxOf(bestScore, score)
                }
            }
            return bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (i in board.indices) {
                if (board[i] == TicTacToe.EMPTY) {
                    board[i] = TicTacToe.X
                    val score = minimax(board, depth + 1, true)
                    board[i] = TicTacToe.EMPTY
                    bestScore = minOf(bestScore, score)
                }
            }
            return bestScore
        }
    }

    private fun getStatusPlayingSound() {
        viewModelScope.launch {
            getStatusPlaySoundUseCaseMatchGame(Unit).collectLatest { result ->
                result.onSuccess { value ->
                    _stateFlow.update {
                        it.copy(
                            isPlayingSoundClick = value
                        )
                    }
                }
            }
        }
    }

    private fun getHighScore() {
        viewModelScope.launch {
            getHighScoreUseCase(Unit).collectLatest { result ->
                result.onSuccess { value ->
                    _stateFlow.update {
                        it.copy(
                            gameWon = value
                        )
                    }
                }
            }
        }
    }

    private fun saveHighScore(score: Int) {
        viewModelScope.launch {
            saveHighScoreUseCase(score).onSuccess {
                getHighScore()
            }
        }
    }

    fun playSoundClick() {
        if (stateFlow.value.isPlayingSoundClick) {
            soundManager.playSound()
        }
    }

    fun onClickReplay() {
        val random = (0..1).random()
        val realPlayer = if (random == 0) {
            TicTacToe.X
        } else {
            TicTacToe.O
        }
        val randomSecond = (0..1).random()
        val currentPlayer = if (randomSecond == 0) {
            TicTacToe.O
        } else {
            TicTacToe.X
        }
        val board = mutableListOf<TicTacToe>()
        repeat(9) {
            board.add(TicTacToe.EMPTY)
        }
        val totalTimeAlive = if (realPlayer == currentPlayer) {
            Constants.TIME_OUT
        } else {
            (Constants.TIME_OUT_MIN..Constants.TIME_OUT_MAX).random()
        }
        _stateFlow.update {
            it.copy(
                timeAlive = totalTimeAlive,
                totalAlive = totalTimeAlive,
                isPlaying = true,
                realPlayer = realPlayer,
                winner = null,
                currentPlayer = currentPlayer,
                board = board,
            )
        }
    }

    private fun checkWinner(board: MutableList<TicTacToe>) {
        var winner = TicTacToe.EMPTY
        val lines = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6),
        )
        for (line in lines) {
            val player = board[line[0]]
            if (player == board[line[1]] && player == board[line[2]] && player != TicTacToe.EMPTY) {
                winner = player
            }
        }

        if (winner != TicTacToe.EMPTY) {
            var gameWon = stateFlow.value.gameWon
            if (winner == stateFlow.value.realPlayer) {
                gameWon++
                saveHighScore(gameWon)
            }
            _stateFlow.update {
                it.copy(
                    winner = winner,
                    gameWon = gameWon,
                    board = board,
                    isPlaying = false,
                )
            }
        } else {
            if (!board.contains(TicTacToe.EMPTY)) {
                _stateFlow.update {
                    it.copy(
                        winner = returnAnotherTicTacToe(stateFlow.value.realPlayer),
                        isPlaying = false,
                    )
                }
            } else {
                val currentPlayer = returnAnotherTicTacToe(stateFlow.value.currentPlayer)
                val totalTimeAlive =
                    if (currentPlayer == stateFlow.value.realPlayer) {
                        Constants.TIME_OUT
                    } else {
                        (Constants.TIME_OUT_MIN..Constants.TIME_OUT_MAX).random()
                    }
                _stateFlow.update {
                    it.copy(
                        currentPlayer = currentPlayer,
                        board = board,
                        timeAlive = totalTimeAlive,
                        totalAlive = totalTimeAlive,
                    )
                }
            }
        }
    }

    fun selectBoard(index: Int) {
        val board = stateFlow.value.board
        board[index] = stateFlow.value.currentPlayer
        checkWinner(board)
    }

    private fun returnAnotherTicTacToe(ticTacToe: TicTacToe): TicTacToe {
        return if (ticTacToe == TicTacToe.X) {
            TicTacToe.O
        } else {
            TicTacToe.X
        }
    }
}

data class NumberGuessingGameState(
    val timeAlive: Long = Constants.TIME_OUT,
    val totalAlive: Long = Constants.TIME_OUT,
    val gameWon: Int = 0,
    val isPlaying: Boolean = true,
    val isPlayingSoundClick: Boolean = true,
    val board: MutableList<TicTacToe> = arrayListOf(
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
    ),
    val currentPlayer: TicTacToe = TicTacToe.X,
    val realPlayer: TicTacToe = TicTacToe.X,
    val winner: TicTacToe? = null,
    val avatarPlayerId: Int = R.drawable.img_1,
    val levelsGame: String = GameLevels.EASY.toString(),
)