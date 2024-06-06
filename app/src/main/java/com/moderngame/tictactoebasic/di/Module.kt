package com.moderngame.tictactoebasic.di

import com.moderngame.tictactoebasic.data.datastore.DataStore
import com.moderngame.tictactoebasic.data.repository.IRepository
import com.moderngame.tictactoebasic.data.soundmanager.SoundManager
import com.moderngame.tictactoebasic.domain.interactors.GetAvtIdUseCase
import com.moderngame.tictactoebasic.domain.interactors.GetGameWonUseCase
import com.moderngame.tictactoebasic.domain.interactors.GetStatusPlaySoundUseCaseMatchGame
import com.moderngame.tictactoebasic.domain.interactors.SaveGameWonUseCase
import com.moderngame.tictactoebasic.domain.interactors.SetAvtIdUseCase
import com.moderngame.tictactoebasic.domain.interactors.SetStatusPlaySoundUseCaseMatchGame
import com.moderngame.tictactoebasic.domain.repository.RepositoryImpl
import com.moderngame.tictactoebasic.presentation.MainViewModel
import com.moderngame.tictactoebasic.presentation.ui.home.HomeViewModel
import com.moderngame.tictactoebasic.presentation.ui.tictactoe.TicTacToeViewModel
import com.moderngame.tictactoebasic.presentation.ui.setting.SettingViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel() }
    factoryOf(::HomeViewModel)
    factoryOf(::TicTacToeViewModel)
    factoryOf(::SettingViewModel)
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val dataSourceModule = module {
    single { DataStore(get()) }
}

val useCaseModule = module {
    factoryOf(::GetGameWonUseCase)
    factoryOf(::SaveGameWonUseCase)
    factoryOf(::GetAvtIdUseCase)
    factoryOf(::SetAvtIdUseCase)
    factoryOf(::SetStatusPlaySoundUseCaseMatchGame)
    factoryOf(::GetStatusPlaySoundUseCaseMatchGame)
}

val repositoryModule = module {
    single<IRepository> { RepositoryImpl(get()) }
}

val mediaPlayerModule = module {
    singleOf(::SoundManager)
}