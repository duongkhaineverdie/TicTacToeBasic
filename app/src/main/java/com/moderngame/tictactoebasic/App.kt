package com.moderngame.tictactoebasic

import android.app.Application
import com.moderngame.tictactoebasic.di.dataSourceModule
import com.moderngame.tictactoebasic.di.dispatcherModule
import com.moderngame.tictactoebasic.di.mediaPlayerModule
import com.moderngame.tictactoebasic.di.repositoryModule
import com.moderngame.tictactoebasic.di.useCaseModule
import com.moderngame.tictactoebasic.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                viewModelModule,
                dispatcherModule,
                dataSourceModule,
                useCaseModule,
                repositoryModule,
                mediaPlayerModule,
            )
        }
    }
}