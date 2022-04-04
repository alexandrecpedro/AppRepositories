package br.com.dio.app.repositories

import android.app.Application
import br.com.dio.app.repositories.data.di.DataModule
import br.com.dio.app.repositories.domain.di.DomainModule
import br.com.dio.app.repositories.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        /** Injecting Data (Repositories, Network and Services), UseCases **/
        // Loading all info from DataModule
        DataModule.load()
        // Loading all info from DomainModule
        DomainModule.load()
        // Loading all info from PresentationModule
        PresentationModule.load()

    }
}