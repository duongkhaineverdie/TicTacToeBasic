package com.moderngame.tictactoebasic.presentation.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moderngame.tictactoebasic.R
import com.moderngame.tictactoebasic.data.soundmanager.SoundManager
import com.moderngame.tictactoebasic.domain.interactors.GetAvtIdUseCase
import com.moderngame.tictactoebasic.domain.interactors.GetHighScoreUseCaseMatchGame
import com.moderngame.tictactoebasic.domain.interactors.GetStatusPlaySoundUseCaseMatchGame
import com.moderngame.tictactoebasic.domain.interactors.SetAvtIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    val getHighScoreUseCase: GetHighScoreUseCaseMatchGame,
    private val getAvtIdUseCase: GetAvtIdUseCase,
    private val setAvtIdUseCase: SetAvtIdUseCase,
    private val getStatusPlaySoundUseCaseMatchGame: GetStatusPlaySoundUseCaseMatchGame,
    private val soundManager: SoundManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getStatusPlayingSound()
        getAvatarId()
        viewModelScope.launch {
            getHighScoreUseCase(Unit).collectLatest {
                it.onSuccess {highScore ->
                    _uiState.update { state ->
                        state.copy(
                            highScore = highScore
                        )
                    }
                }
            }
        }
    }

    fun setAvatarId(id: Int) {
        viewModelScope.launch {
            setAvtIdUseCase(id).onSuccess {
                getAvatarId()
            }
        }
    }

    private fun getAvatarId() {
        viewModelScope.launch {
            getAvtIdUseCase(Unit).collectLatest { result ->
                result.onSuccess { value ->
                    _uiState.update {
                        it.copy(
                            avatarId = value,
                            isShowDialogChooseAvatar = false,
                        )
                    }
                }
            }
        }
    }

    private fun getStatusPlayingSound() {
        viewModelScope.launch {
            getStatusPlaySoundUseCaseMatchGame(Unit).collectLatest { result ->
                result.onSuccess { value ->
                    _uiState.update {
                        it.copy(
                            isPlayingSoundClick = value
                        )
                    }
                }
            }
        }
    }

    fun showDialogChooseAvatar() {
        _uiState.update {
            it.copy(
                isShowDialogChooseAvatar = true
            )
        }
    }

    fun playSoundClick() {
        if (uiState.value.isPlayingSoundClick){
            soundManager.playSound()
        }
    }

    fun onClickStartGame(){
        _uiState.update {
            it.copy(
                isShowDialogSelectLevel = true
            )
        }
    }

    fun onDismissDialogLevels(){
        _uiState.update {
            it.copy(
                isShowDialogSelectLevel = false
            )
        }
    }

}

data class HomeUiState(
    val highScore: Int = 0,
    val isShowDialogChooseAvatar: Boolean = false,
    val avatarId: Int = R.drawable.img_1,
    val isPlayingSoundClick: Boolean = true,
    val isShowDialogSelectLevel: Boolean = false,
)